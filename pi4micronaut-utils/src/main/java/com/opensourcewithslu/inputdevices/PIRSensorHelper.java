package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The PIRSensorHelper class is used to initialize a PIR motion sensor.
 */
@Singleton
public class PIRSensorHelper {

    private static final Logger log = LoggerFactory.getLogger(PIRSensorHelper.class);

    private final DigitalInput pirSensorInput;

    private DigitalStateChangeListener pirSensorInputListener;

    /**
     * Shows if the PIR sensor detects movement or not.
     */
    public boolean isMoving;

    /**
     * PIRSensorHelper constructor
     * @param pirSensorOutput A Pi4J DigitalInput object.
     */
    public PIRSensorHelper(DigitalInput pirSensorOutput)
    {
        this.pirSensorInput = pirSensorOutput;
        this.isMoving = pirSensorInput.isHigh();

        initialize();
    }

    /**
     * Initializes the listener that keeps track of if the PIR sensor detects motion or not. It is automatically called when the PIRSensorHelper is instantiated.
     */
    public void initialize()
    {
        log.info("Initializing PIR Sensor");

        pirSensorInputListener = e-> isMoving = pirSensorInput.isHigh();
        pirSensorInput.addListener(pirSensorInputListener);
    }

    /**
     * Adds an event listener to the PIR sensor.
     * @param function A Pi4J DigitalStateChangeListener object.
     */
    public void addEventListener(DigitalStateChangeListener function)
    {
        log.info("Adding event listener");

        pirSensorInputListener = function;
        pirSensorInput.addListener(pirSensorInputListener);
    }

    /**
     * Removes the event listener from the PIR sensor.
     */
    public void removeEventListener()
    {
        log.info("Removing event listener");

        if (pirSensorInputListener != null) {
            pirSensorInput.removeListener(pirSensorInputListener);
            pirSensorInputListener = null;
        }
    }
}