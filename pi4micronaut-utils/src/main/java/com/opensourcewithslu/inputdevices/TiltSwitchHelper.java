package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TiltSwitchHelper {

    private static final Logger log = LoggerFactory.getLogger(TiltSwitchHelper.class);
    private final DigitalInput tiltSwitchInput;
    private DigitalStateChangeListener tiltSwitchInputListener;
    public boolean isTilted;

    public TiltSwitchHelper(DigitalInput tiltSwitchInput)
    {
        this.tiltSwitchInput = tiltSwitchInput;
        this.isTilted = tiltSwitchInput.isHigh();

        initialize();
    }

    public void initialize()
    {
        log.info("Initializing Tilt Switch");

        tiltSwitchInputListener = e-> isTilted = tiltSwitchInput.isHigh();
        tiltSwitchInput.addListener(tiltSwitchInputListener);
    }

    public void addEventListener(DigitalStateChangeListener function)
    {
        tiltSwitchInputListener = function;
        tiltSwitchInput.addListener(tiltSwitchInputListener);
    }

    public void removeEventListener()
    {
        if (tiltSwitchInputListener != null) {
            tiltSwitchInput.removeListener(tiltSwitchInputListener);
            tiltSwitchInputListener = null;
        }
    }
}
