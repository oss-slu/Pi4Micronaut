package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.DHT11Helper;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;


@Controller("/dht11")
public class DHT11Controller {
    private final DHT11Helper dht11;

    public DHT11Controller(
                           @Named("DHT11-data-output")DigitalOutput dht11Output) {

        this.dht11 = new DHT11Helper( dht11Output);
    }

    @Get("/read")
    public void readSensorData() {
        try{
            dht11.readData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}