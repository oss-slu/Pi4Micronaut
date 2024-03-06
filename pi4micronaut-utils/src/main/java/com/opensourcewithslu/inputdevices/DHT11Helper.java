package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DHT11Helper {

    private static final Logger log = LoggerFactory.getLogger(DHT11Helper.class);

    private final DigitalInput dht11Input;

    public DHT11Helper(DigitalInput sensorInput)
    {
        this.dht11Input = sensorInput;
    }
}
