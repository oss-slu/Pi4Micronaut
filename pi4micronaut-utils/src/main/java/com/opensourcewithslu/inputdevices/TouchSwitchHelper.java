package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The TouchSwitchHelper class is used to initialize a touch switch.
 */
public class TouchSwitchHelper {

    private static final Logger log = LoggerFactory.getLogger(TouchSwitchHelper.class);

    private final DigitalInput touchSwitchInput;

    private DigitalStateChangeListener touchSwitchInputListener;

    /**
     * Shows if the touch switch has been touched.
     */
    public boolean isTouched;

    /**
     * TouchSwitchHelper constructor.
     * @param touchSwitchInput A Pi4J DigitalInput object.
     */
    public TouchSwitchHelper(DigitalInput touchSwitchInput)
    {
        this.touchSwitchInput = touchSwitchInput;
        this.isTouched = touchSwitchInput.isHigh();

        initialize();
    }

    /**
     * Initializes the listener that keeps track of if the touch switch has been touched or not. It is automatically called when the TouchSwitchHelper is instantiated.
     */
    private void initialize()
    {
        log.info("Initializing Touch Switch");

        touchSwitchInputListener = e-> isTouched = touchSwitchInput.isHigh();
        touchSwitchInput.addListener(touchSwitchInputListener);
    }

    /**
     * Adds an event listener to the touch switch.
     * @param function A Pi4J DigitalStateChangeListener object.
     */
    public void addEventListener(DigitalStateChangeListener function)
    {
        touchSwitchInputListener = function;
        touchSwitchInput.addListener(touchSwitchInputListener);
    }

    /**
     * Removes the event listener from the touch switch.
     */
    public void removeEventListener()
    {
        if (touchSwitchInputListener != null) {
            touchSwitchInput.removeListener(touchSwitchInputListener);
            touchSwitchInputListener = null;
        }
    }
}
