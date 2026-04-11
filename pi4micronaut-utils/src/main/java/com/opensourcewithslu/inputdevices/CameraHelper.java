package com.opensourcewithslu.inputdevices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * The CameraHelper class enables interaction with a Raspberry Pi CSI camera
 * module.
 * It supports capturing images and recording video using the libcamera toolset.
 * Supported modes:
 * <ul>
 * <li>1920x1080 @ 30 fps</li>
 * <li>1280x720 @ 60 fps</li>
 * <li>640x480 @ 60 or 90 fps</li>
 * </ul>
 */
public class CameraHelper {

    private static final Logger log = LoggerFactory.getLogger(CameraHelper.class);

    /** Supported resolution presets (widthxheight). */
    public static final List<String> SUPPORTED_RESOLUTIONS = Arrays.asList(
            "1920x1080", "1280x720", "640x480");

    /** Maximum frame rates allowed per resolution. */
    public static final Map<String, Integer> MAX_FPS_PER_RESOLUTION = Map.of(
            "1920x1080", 30,
            "1280x720", 60,
            "640x480", 90);

    private String resolution;
    private int frameRate;
    private boolean initialized;
    private boolean recording;
    private Process videoProcess;

    /**
     * CameraHelper constructor.
     *
     * @param resolution The initial resolution (e.g. "1920x1080").
     * @param frameRate  The initial frame rate (e.g. 30).
     */
    public CameraHelper(String resolution, int frameRate) {
        this.resolution = resolution;
        this.frameRate = frameRate;
        this.initialized = false;
        this.recording = false;
        this.videoProcess = null;
    }

    // -------------------------------------------------------------------------
    // Initialization
    // -------------------------------------------------------------------------

    /**
     * Initializes the camera and validates configuration.
     * Must be called before capturing photos or recording video.
     */
    public void initialize() {
        log.info("Initializing camera with resolution={} frameRate={}", resolution, frameRate);

        if (!SUPPORTED_RESOLUTIONS.contains(resolution)) {
            log.error("Unsupported resolution: {}. Supported values: {}", resolution, SUPPORTED_RESOLUTIONS);
            throw new IllegalArgumentException("Unsupported resolution: " + resolution);
        }

        int maxFps = MAX_FPS_PER_RESOLUTION.get(resolution);
        if (frameRate <= 0 || frameRate > maxFps) {
            log.error("Invalid frame rate {} for resolution {}. Max allowed: {}", frameRate, resolution, maxFps);
            throw new IllegalArgumentException(
                    "Invalid frame rate " + frameRate + " for resolution " + resolution + ". Max: " + maxFps);
        }

        initialized = true;
        log.info("Camera initialized successfully.");
    }

    // -------------------------------------------------------------------------
    // Configuration
    // -------------------------------------------------------------------------

    /**
     * Sets the camera resolution.
     *
     * @param resolution A supported resolution string (e.g. "1280x720").
     */
    public void setResolution(String resolution) {
        if (!SUPPORTED_RESOLUTIONS.contains(resolution)) {
            log.error("Unsupported resolution: {}", resolution);
            throw new IllegalArgumentException("Unsupported resolution: " + resolution);
        }
        log.info("Setting resolution to {}", resolution);
        this.resolution = resolution;

        // Re-validate frame rate against the new resolution ceiling
        int maxFps = MAX_FPS_PER_RESOLUTION.get(resolution);
        if (this.frameRate > maxFps) {
            log.warn("Current frame rate {} exceeds max {} for {}. Clamping to {}.",
                    this.frameRate, maxFps, resolution, maxFps);
            this.frameRate = maxFps;
        }
    }

    /**
     * Sets the camera frame rate.
     *
     * @param frameRate Desired frames per second (must be valid for the current
     *                  resolution).
     */
    public void setFrameRate(int frameRate) {
        int maxFps = MAX_FPS_PER_RESOLUTION.getOrDefault(resolution, 30);
        if (frameRate <= 0 || frameRate > maxFps) {
            log.error("Invalid frame rate {} for resolution {}. Max allowed: {}", frameRate, resolution, maxFps);
            throw new IllegalArgumentException(
                    "Invalid frame rate " + frameRate + " for resolution " + resolution + ". Max: " + maxFps);
        }
        log.info("Setting frame rate to {} fps", frameRate);
        this.frameRate = frameRate;
    }

    /**
     * Returns the current resolution.
     *
     * @return Current resolution string.
     */
    public String getResolution() {
        return resolution;
    }

    /**
     * Returns the current frame rate.
     *
     * @return Current frame rate in fps.
     */
    public int getFrameRate() {
        return frameRate;
    }

    /**
     * Returns whether the camera has been initialized.
     *
     * @return true if initialized.
     */
    public boolean isInitialized() {
        return initialized;
    }

    /**
     * Returns whether a video recording is currently in progress.
     *
     * @return true if recording.
     */
    public boolean isRecording() {
        return recording;
    }

    // -------------------------------------------------------------------------
    // Photo Capture
    // -------------------------------------------------------------------------

    /**
     * Captures a single photo and saves it to the specified file path.
     *
     * @param outputPath Destination file path (e.g. "/home/pi/photo.jpg").
     * @throws IllegalStateException if the camera has not been initialized.
     * @throws IOException           if the libcamera-still process fails to start.
     */
    public void capturePhoto(String outputPath) throws IOException {
        if (!initialized) {
            log.error("Camera is not initialized. Call initialize() first.");
            throw new IllegalStateException("Camera is not initialized. Call initialize() first.");
        }

        String[] wh = resolution.split("x");
        String width = wh[0];
        String height = wh[1];

        log.info("Capturing photo: resolution={} output={}", resolution, outputPath);

        List<String> command = Arrays.asList(
                "libcamera-still",
                "--width", width,
                "--height", height,
                "--output", outputPath,
                "--nopreview");

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);

        try {
            Process process = pb.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                log.error("libcamera-still exited with code {}", exitCode);
                throw new IOException("libcamera-still failed with exit code: " + exitCode);
            }
            log.info("Photo captured successfully: {}", outputPath);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Photo capture was interrupted: {}", e.getMessage());
            throw new IOException("Photo capture interrupted", e);
        }
    }

    // -------------------------------------------------------------------------
    // Video Recording
    // -------------------------------------------------------------------------

    /**
     * Starts recording video to the specified file path.
     * Recording runs until {@link #stopVideoCapture()} is called.
     *
     * @param outputPath Destination file path (e.g. "/home/pi/video.h264").
     * @throws IllegalStateException if the camera is not initialized or already
     *                               recording.
     * @throws IOException           if the libcamera-vid process fails to start.
     */
    public void startVideoCapture(String outputPath) throws IOException {
        if (!initialized) {
            log.error("Camera is not initialized. Call initialize() first.");
            throw new IllegalStateException("Camera is not initialized. Call initialize() first.");
        }
        if (recording) {
            log.warn("Video capture is already in progress.");
            return;
        }

        String[] wh = resolution.split("x");
        String width = wh[0];
        String height = wh[1];

        log.info("Starting video capture: resolution={} frameRate={} output={}", resolution, frameRate, outputPath);

        List<String> command = Arrays.asList(
                "libcamera-vid",
                "--width", width,
                "--height", height,
                "--framerate", String.valueOf(frameRate),
                "--output", outputPath,
                "--nopreview",
                "--timeout", "0" // record until explicitly stopped
        );

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);

        videoProcess = pb.start();
        recording = true;
        log.info("Video capture started: {}", outputPath);
    }

    /**
     * Stops an in-progress video recording.
     * If no recording is active, this method is a no-op.
     */
    public void stopVideoCapture() {
        if (!recording || videoProcess == null) {
            log.warn("No video capture is currently in progress.");
            return;
        }

        log.info("Stopping video capture.");
        videoProcess.destroy();

        try {
            videoProcess.waitFor();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Interrupted while waiting for video process to terminate: {}", e.getMessage());
        }

        videoProcess = null;
        recording = false;
        log.info("Video capture stopped.");
    }

    // -------------------------------------------------------------------------
    // Cleanup
    // -------------------------------------------------------------------------

    /**
     * Releases camera resources. Stops any active recording and marks the helper as
     * uninitialized.
     */
    public void cleanup() {
        log.info("Cleaning up camera resources.");
        if (recording) {
            stopVideoCapture();
        }
        initialized = false;
        log.info("Camera resources released.");
    }

    // -------------------------------------------------------------------------
    // Package-private setter for testing
    // -------------------------------------------------------------------------

    /**
     * Sets a custom video process (used in unit tests to inject a mock Process).
     *
     * @param process The mock or stub Process to inject.
     */
    void setVideoProcess(Process process) {
        this.videoProcess = process;
    }
}