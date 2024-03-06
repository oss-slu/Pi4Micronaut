package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DHT11Helper {

    private static final Logger log = LoggerFactory.getLogger(DHT11Helper.class);

    private final DigitalOutput dht11Output;

    public DHT11Helper(DigitalOutput sensorOutput)
    {
        this.dht11Output = sensorOutput;
    }
}
