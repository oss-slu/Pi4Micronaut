package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.digital.DigitalOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PIRSensorHelper {

    private static final Logger log = LoggerFactory.getLogger(PIRSensorHelper.class);

    private final DigitalOutput pirSensorInput;

    public PIRSensorHelper(DigitalOutput pirSensorInput)
    {
        this.pirSensorInput = pirSensorInput;
    }


}
