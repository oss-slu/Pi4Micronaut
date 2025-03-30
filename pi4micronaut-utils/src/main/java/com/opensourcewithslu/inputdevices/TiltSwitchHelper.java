package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class to control a tilt switch using Digital Input.
 * This class provides methods to initialize a tilt switch by adding or removing an event listener.
 */
public class TiltSwitchHelper {

    private static final Logger log = LoggerFactory.getLogger(TiltSwitchHelper.class);
    private final DigitalInput tiltSwitchInput;
    private DigitalStateChangeListener tiltSwitchInputListener;
    /**
     * Boolean variable that is true if the tilt switch is being tilted and false otherwise.
     */
    public boolean isTilted;

    /**
     * TiltSwitchHelper constructor.
     * @param tiltSwitchInput A Pi4J DigitalInput object.
     */
    public TiltSwitchHelper(DigitalInput tiltSwitchInput)
    {
        this.tiltSwitchInput = tiltSwitchInput;
        this.isTilted = tiltSwitchInput.isHigh();

        initialize();
    }

    /**
     * Initializes the listener that keeps track of if the tilt switch is tilted.
     * It is automatically called when the TiltSwitchHelper is instantiated.
     */
    private void initialize()
    {
        log.info("Initializing Tilt Switch");

        tiltSwitchInputListener = e-> {
            isTilted = tiltSwitchInput.isHigh();
            log.info("Tilt Switch state changed. New state is: {}", isTilted ? "TILTED" : "NOT TILTED");
        };
        tiltSwitchInput.addListener(tiltSwitchInputListener);
    }

    /**
     * Adds an event listener to the tilt switch.
     * @param function A Pi4J DigitalStateChangeListener object.
     */
    public void addEventListener(DigitalStateChangeListener function)
    {
        tiltSwitchInputListener = function;
        tiltSwitchInput.addListener(tiltSwitchInputListener);
    }

    /**
     * Removes the event listener from the tilt switch.
     */
    public void removeEventListener()
    {
        if (tiltSwitchInputListener != null) {
            tiltSwitchInput.removeListener(tiltSwitchInputListener);
            tiltSwitchInputListener = null;
        }
    }
}

