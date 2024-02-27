package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PIRSensorHelper {

    private static final Logger log = LoggerFactory.getLogger(PIRSensorHelper.class);

    private final DigitalOutput pirSensorOutput;

    private DigitalStateChangeListener pirSensorOutputListener;

    public boolean isMoving;

    public PIRSensorHelper(DigitalOutput pirSensorOutput)
    {
        this.pirSensorOutput = pirSensorOutput;
        this.isMoving = pirSensorOutput.isHigh();

        initialize();
    }

    public void initialize()
    {
        log.info("Initializing PIR Sensor");

        pirSensorOutputListener = e-> isMoving = pirSensorOutput.isHigh();
        pirSensorOutput.addListener(pirSensorOutputListener);
    }

    public void addEventListener(DigitalStateChangeListener function)
    {
        log.info("Adding event listener");

        pirSensorOutputListener = function;
        pirSensorOutput.addListener(pirSensorOutputListener);
    }

    public void removeEventListener()
    {
        log.info("Removing event listener");

        if (pirSensorOutputListener != null) {
            pirSensorOutput.removeListener(pirSensorOutputListener);
            pirSensorOutputListener = null;
        }
    }
}