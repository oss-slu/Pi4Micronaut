package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The MicroSwitchHelper class is used to initialize a micro switch.
 */
public class MicroSwitchHelper {

    private static Logger log = LoggerFactory.getLogger(MicroSwitchHelper.class);

    private final DigitalInput microSwitchInput;

    private DigitalStateChangeListener microSwitchInputListener;

    /**
     * Shows if the micro switch has been pressed.
     */
    public boolean isPressed;

    /**
     * MicroSwitchHelper constructor.
     * @param microSwitchInput A Pi4J DigitalInput object.
     */
    public MicroSwitchHelper(DigitalInput microSwitchInput)
    {
        this.microSwitchInput = microSwitchInput;
        this.isPressed = microSwitchInput.isHigh();

        initialize();
    }

    /**
     * Initializes the listener that keeps track of if the micro switch has been pressed or not. It is automatically called when the MicroSwitchHelper is instantiated.
     */
    public void initialize()
    {
        log.info("Initializing Micro Switch");

        microSwitchInputListener = e-> isPressed = microSwitchInput.isHigh();
        microSwitchInput.addListener(microSwitchInputListener);
    }

    /**
     * Adds an event listener to the micro switch.
     * @param function A Pi4J DigitalStateChangeListener object.
     */
    public void addEventListener(DigitalStateChangeListener function)
    {
        microSwitchInputListener = function;
        microSwitchInput.addListener(microSwitchInputListener);
    }

    /**
     * Removes the event listener from the micro switch.
     */
    public void removeEventListener()
    {
        if (microSwitchInputListener != null) {
            microSwitchInput.removeListener(microSwitchInputListener);
            microSwitchInputListener = null;
        }
    }


    /**
     * Sets the logger object.
     * This method is intended for internal testing purposes only.
     * 
     * @param log Logger object to set the logger to.
     */
    void setLog (Logger log){
        this.log = log;
    }
}
