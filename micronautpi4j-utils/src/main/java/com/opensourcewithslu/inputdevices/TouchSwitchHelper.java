package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TouchSwitchHelper {

    private static final Logger log = LoggerFactory.getLogger(SlideSwitchHelper.class);

    private final DigitalInput touchSwitchInput;

    private DigitalStateChangeListener touchSwitchInputListener;

    public boolean isTouched;

    public TouchSwitchHelper(DigitalInput touchSwitchInput)
    {
        this.touchSwitchInput = touchSwitchInput;
        this.isTouched = touchSwitchInput.isHigh();
        initialize();
    }

    public void initialize()
    {
        log.info("Initializing Touch Switch");

        touchSwitchInputListener = e-> isTouched = touchSwitchInput.isHigh();
        touchSwitchInput.addListener(touchSwitchInputListener);
    }

    public void addEventListener(DigitalStateChangeListener function)
    {
        touchSwitchInputListener = function;
        touchSwitchInput.addListener(touchSwitchInputListener);
    }

    public void removeEventListener()
    {
        if (touchSwitchInputListener != null) {
            touchSwitchInput.removeListener(touchSwitchInputListener);
            touchSwitchInputListener = null;
        }
    }

}
