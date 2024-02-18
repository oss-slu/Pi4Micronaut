package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TouchSwitchHelper {

    private static final Logger log = LoggerFactory.getLogger(SlideSwitchHelper.class);

    private final DigitalInput touchSwitchInput;

    public boolean isTouched;

    public boolean isReleased;

    public TouchSwitchHelper(DigitalInput touchSwitchInput)
    {
        this.touchSwitchInput = touchSwitchInput;

        initialize();
    }

    public void initialize()
    {
        log.info("Initializing Touch Switch");

        touchSwitchInput.addListener(e-> {
            isTouched = touchSwitchInput.isHigh();
            isReleased = touchSwitchInput.isLow();
        });
    }

    public void addEventListener(DigitalStateChangeListener function)
    {
        touchSwitchInput.addListener(function);
    }

    public void removeEventListener(DigitalStateChangeListener function)
    {
        touchSwitchInput.removeListener(function);
    }
}
