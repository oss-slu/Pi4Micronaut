package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThermistorHelper {
    private static final Logger log = LoggerFactory.getLogger(ThermistorHelper.class);
    private String helperName;
    private int globalValue;

    public ThermistorHelper(DigitalInput sensorInput, DigitalOutput sensorOutput){
        //maybe use multipin
    }

    public int getThermistorValue() {
        return 1;
    }
}
