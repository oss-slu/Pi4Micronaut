package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.DigitalStateChangeEvent;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import io.micronaut.context.annotation.Primary;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The IRObstacleAvoidance class provides functionality for interfacing with an IR obstacle avoidance module.
 * This sensor detects obstacles and outputs a LOW signal when an obstacle is detected.
 * The detection distance can be adjusted using the potentiometer on the module.
 */
@Primary
public class IRObstacleAvoidanceHelper {
    private static final Logger log = LoggerFactory.getLogger(IRObstacleAvoidanceHelper.class);
    
    private final DigitalInput digitalInput;
    private DigitalStateChangeListener listener;
    private volatile boolean obstacleDetected;

    /**
     * Initializes the IR obstacle avoidance module with the specified digital input.
     *
     * @param digitalInput The Pi4J DigitalInput instance connected to the IR sensor's output pin
     * @throws IllegalArgumentException if digitalInput is null
     */
    public  IRObstacleAvoidanceHelper( DigitalInput digitalInput) {
        if (digitalInput == null) {
            String errorMsg = "DigitalInput cannot be null for IR obstacle avoidance module";
            log.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        
        this.digitalInput = digitalInput;
        this.obstacleDetected = digitalInput.state() == DigitalState.LOW;
        
        log.info("IR obstacle avoidance module initialized on pin {}", digitalInput.id());
        log.debug("Initial state: {}", obstacleDetected ? "Obstacle detected" : "No obstacle");
    }

    /**
     * Checks if an obstacle is currently detected.
     * The sensor outputs LOW when an obstacle is detected, HIGH when no obstacle is present.
     *
     * @return true if an obstacle is detected, false otherwise
     */
    public boolean isObstacleDetected() {
        DigitalState currentState = digitalInput.state();
        obstacleDetected = (currentState == DigitalState.LOW);
        
        log.trace("Obstacle detection check - Current state: {}, Obstacle detected: {}", currentState, obstacleDetected);
        
        return obstacleDetected;
    }

    /**
     * Gets the raw digital state from the sensor.
     *
     * @return The current DigitalState (HIGH when no obstacle, LOW when obstacle detected)
     */
    public DigitalState getState() {
        DigitalState state = digitalInput.state();
        log.trace("Raw state query - Current state: {}", state);
        return state;
    }

    /**
     * Registers a listener to be notified when the obstacle detection state changes.
     * Only one listener can be registered at a time. Calling this method again will replace
     * the previous listener.
     *
     * @param function The callback to execute when state changes
     * @throws IllegalArgumentException if eventConsumer is null
     */
    public void addEventListener(DigitalStateChangeListener function) {
        if (function == null) {
            String errorMsg = "Event listener cannot be null";
            log.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        // Remove existing listener if present
        if (listener != null) {
            digitalInput.removeListener(listener);
            log.debug("Removed previous event listener");
        }

        listener = function;
        digitalInput.addListener(listener);
        log.info("Event listener registered for IR obstacle avoidance module");
    }

    /**
     * Removes the currently registered event listener.
     */
    public void removeEventListener() {
        if (listener != null) {
            digitalInput.removeListener(listener);
            listener = null;
            log.info("Event listener removed from IR obstacle avoidance module");
        } else {
            log.debug("No event listener to remove");
        }
    }

    /**
     * Waits for an obstacle to be detected with a timeout.
     *
     * @param timeoutMillis Maximum time to wait in milliseconds
     * @return true if an obstacle was detected within the timeout, false otherwise
     * @throws IllegalArgumentException if timeoutMillis is negative
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    public boolean waitForObstacle(long timeoutMillis) throws InterruptedException {
        if (timeoutMillis < 0) {
            String errorMsg = "Timeout cannot be negative";
            log.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        log.debug("Waiting for obstacle detection with timeout: {}ms", timeoutMillis);
        
        long startTime = System.currentTimeMillis();
        long elapsedTime;
        
        while ((elapsedTime = System.currentTimeMillis() - startTime) < timeoutMillis) {
            if (isObstacleDetected()) {
                log.info("Obstacle detected after {}ms", elapsedTime);
                return true;
            }
            Thread.sleep(10); // Poll every 10ms
        }
        
        log.debug("Timeout reached after {}ms - No obstacle detected", timeoutMillis);
        return false;
    }

    /**
     * Gets the current obstacle detection status as a human-readable string.
     *
     * @return "Obstacle detected" or "No obstacle"
     */
    public String getStatusMessage() {
        return obstacleDetected ? "Obstacle detected" : "No obstacle";
    }

    /**
     * Shuts down the IR obstacle avoidance module and cleans up resources.
     */
    public void shutdown() {
    log.info("Shutting down IR obstacle avoidance module");

    try {
        removeEventListener();
        log.info("IR obstacle avoidance module shutdown complete");
    } catch (Exception e) {
        log.error("Error during IR obstacle avoidance module shutdown", e);
    }
    }

}

