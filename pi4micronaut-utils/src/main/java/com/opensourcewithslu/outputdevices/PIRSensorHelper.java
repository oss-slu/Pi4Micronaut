package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PIRSensorHelper {

    private static final Logger log = LoggerFactory.getLogger(PIRSensorHelper.class);

    private final DigitalInput pirSensorInput;

    private DigitalStateChangeListener pirSensorInputListener;

    public boolean isMoving;

    public PIRSensorHelper(DigitalInput pirSensorOutput)
    {
        this.pirSensorInput = pirSensorOutput;
        this.isMoving = pirSensorInput.isHigh();

        initialize();
    }

    public void initialize()
    {
        log.info("Initializing PIR Sensor");

        pirSensorInputListener = e-> isMoving = pirSensorInput.isHigh();
        pirSensorInput.addListener(pirSensorInputListener);
    }

    public void addEventListener(DigitalStateChangeListener function)
    {
        log.info("Adding event listener");

        pirSensorInputListener = function;
        pirSensorInput.addListener(pirSensorInputListener);
    }

    public void removeEventListener()
    {
        log.info("Removing event listener");

        if (pirSensorInputListener != null) {
            pirSensorInput.removeListener(pirSensorInputListener);
            pirSensorInputListener = null;
        }
    }
}