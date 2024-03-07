package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.DHT11Helper;
import com.pi4j.io.gpio.digital.DigitalInput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

@Controller("/dht11Sensor")
public class DHT11Controller {

    private final DHT11Helper dht11Helper;

    public DHT11Controller(@Named("dht11-sensor-input") DigitalInput dht11In) {
        this.dht11Helper = new DHT11Helper(dht11In);
    }

    @Get("/getTemp")
    public float getTemp(){
        return dht11Helper.readTemp();
    }

    @Get("/getHumidity")
    public float getHumidity() {
        return dht11Helper.readHumidity();
    }
}
