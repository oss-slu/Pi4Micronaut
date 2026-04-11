package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.CameraHelper;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The CameraController class provides REST endpoints that demonstrate
 * how to use the {@link CameraHelper} class to capture photos and record video.
 */
// tag::ex[]
@Controller("/camera")
public class CameraController {

    private static final Logger log = LoggerFactory.getLogger(CameraController.class);

    private final CameraHelper cameraHelper;

    /**
     * CameraController constructor. Defaults to 1920x1080 @ 30 fps.
     */
    public CameraController() {
        this.cameraHelper = new CameraHelper("1920x1080", 30);
    }

    /**
     * Initializes the camera on startup.
     */
    @PostConstruct
    public void init() {
        cameraHelper.initialize();
    }

    /**
     * Captures a single photo and saves it to the given path.
     *
     * @param outputPath File path for the captured image (default:
     *                   /home/pi/photo.jpg).
     * @return A message indicating success or failure.
     */
    @Get("/capture")
    public String capturePhoto(@QueryValue(defaultValue = "/home/pi/photo.jpg") String outputPath) {
        try {
            cameraHelper.capturePhoto(outputPath);
            return "Photo captured successfully: " + outputPath;
        } catch (IOException e) {
            log.error("Failed to capture photo: {}", e.getMessage());
            return "Error capturing photo: " + e.getMessage();
        }
    }

    /**
     * Starts video recording and saves it to the given path.
     *
     * @param outputPath File path for the video output (default:
     *                   /home/pi/video.h264).
     * @return A message indicating success or failure.
     */
    @Get("/startVideo")
    public String startVideo(@QueryValue(defaultValue = "/home/pi/video.h264") String outputPath) {
        try {
            cameraHelper.startVideoCapture(outputPath);
            return "Video recording started: " + outputPath;
        } catch (IOException e) {
            log.error("Failed to start video capture: {}", e.getMessage());
            return "Error starting video: " + e.getMessage();
        }
    }

    /**
     * Stops the current video recording.
     *
     * @return A message confirming recording has stopped.
     */
    @Get("/stopVideo")
    public String stopVideo() {
        cameraHelper.stopVideoCapture();
        return "Video recording stopped.";
    }

    /**
     * Changes the camera resolution.
     * Supported values: 1920x1080, 1280x720, 640x480.
     *
     * @param resolution The desired resolution string.
     * @return A message confirming the resolution change.
     */
    @Get("/setResolution")
    public String setResolution(@QueryValue String resolution) {
        try {
            cameraHelper.setResolution(resolution);
            return "Resolution set to: " + resolution;
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Changes the camera frame rate.
     *
     * @param fps The desired frames per second.
     * @return A message confirming the frame rate change.
     */
    @Get("/setFrameRate")
    public String setFrameRate(@QueryValue int fps) {
        try {
            cameraHelper.setFrameRate(fps);
            return "Frame rate set to: " + fps + " fps";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Returns the current camera status (resolution, frame rate, recording state).
     *
     * @return A status string.
     */
    @Get("/status")
    public String status() {
        return String.format("Resolution: %s | Frame Rate: %d fps | Recording: %s | Initialized: %s",
                cameraHelper.getResolution(),
                cameraHelper.getFrameRate(),
                cameraHelper.isRecording(),
                cameraHelper.isInitialized());
    }

    /**
     * Cleans up camera resources on shutdown.
     */
    @PreDestroy
    public void destroy() {
        cameraHelper.cleanup();
    }
}
// end::ex[]