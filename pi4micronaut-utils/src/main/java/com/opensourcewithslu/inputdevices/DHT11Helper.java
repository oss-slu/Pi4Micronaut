package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DHT11Helper {

    private static final Logger log = LoggerFactory.getLogger(DHT11Helper.class);

    private final DigitalInput dht11Input;

    private DigitalStateChangeListener dht11InputListener;

    public float temperature;

    public float humidity;

    private int[] byteStream = new int[5];

    public DHT11Helper(DigitalInput sensorInput)
    {
        this.dht11Input = sensorInput;

        initialize();
    }

    public void initialize()
    {
        log.info("Initializing DHT11 Sensor");
        //TODO
    }
}
