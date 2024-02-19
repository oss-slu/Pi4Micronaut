package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LightSensorHelper {

    private static final Logger log = LoggerFactory.getLogger(LightSensorHelper.class);
    private final DigitalInput lightInput;

    public LightSensorHelper(DigitalInput lightInput) {
        this.lightInput = lightInput;

    }


}
