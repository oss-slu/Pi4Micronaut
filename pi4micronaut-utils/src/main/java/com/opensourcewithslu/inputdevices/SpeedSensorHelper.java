package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.*;
import java.time.Instant;
import java.time.Duration;

/**
 * The SpeedSensorHelper class initializes the speed sensor and provides component functionality
 */
public class SpeedSensorHelper {
    private static final Logger log = LoggerFactory.getLogger(SpeedSensorHelper.class);
    private final DigitalInput sensorPin; // This is the pin that receives pulse input from the speed sensor
    private volatile boolean sensorActive;
    private volatile double rpm;
    private final double pulsesPerRevolution;
    private Instant lastPulseTime;
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    /**
     * Constructs a new SpeedSensorHelper instance.
     *
     * @param sensorPin The DigitalInput pin for receiving the pulse from the speed sensor.
     * @param pulsesPerRevolution Number of pulses per revolution from the speed sensor.
     */
    public SpeedSensorHelper(DigitalInput sensorPin, double pulsesPerRevolution) {
        this.sensorPin = sensorPin;
        this.pulsesPerRevolution = pulsesPerRevolution;
        initialize();
    }

    /**
     * Initializes the speed sensor
     */
    public void initialize() {
        log.info("Speed Sensor Initialized");
        sensorActive = true;
        lastPulseTime = Instant.now();
    }

    /**
     * Begins measuring speed and calculating RPM at regular intervals
     */
    public void startMeasuring() {
        if (!sensorActive) {
            return;
        }
        executorService.scheduleAtFixedRate(this::calculateSpeed, 0, 100, TimeUnit.MILLISECONDS);
    }

    /**
     * Calculates speed (RPM) based on pulse intervals
     */
    private void calculateSpeed() {
        try {
            Instant currentTime = Instant.now();
            Duration timeBetweenPulses = Duration.between(lastPulseTime, currentTime);
            lastPulseTime = currentTime;

            double secondsBetweenPulses = timeBetweenPulses.toMillis() / 1000.0;
            rpm = (1.0 / secondsBetweenPulses) * 60.0 / pulsesPerRevolution;
            log.info("Speed (RPM): {}", rpm);
        } catch (Exception e) {
            log.error("Error in measuring speed", e);
            rpm = 0;
        }
    }

    /**
     * Returns the speed in RPM.
     *
     * @return The speed value in RPM.
     */
    public double getRPM() {
        return rpm;
    }

    /**
     * Stops the sensor measurement.
     */
    public void stopMeasuring() {
        sensorActive = false;
        if (!executorService.isShutdown()) {
            executorService.shutdownNow();
        }
    }
}