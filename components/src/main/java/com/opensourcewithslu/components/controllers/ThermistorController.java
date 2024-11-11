package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.ThermistorHelper;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Controller("/thermistor")
public class ThermistorController {

    private final ThermistorHelper thermistorHelper;

    // Inject Pi4J Context and digital pins for the thermistor
    @Inject
    public ThermistorController(Context pi4jContext) {

        // Initialize the ThermistorHelper with SPI configuration
        this.thermistorHelper = new ThermistorHelper(pi4jContext);
    }

    // Endpoint to get temperature in Celsius
    @Get("/temperature/celsius")
    public double getTemperatureCelsius() {
        return thermistorHelper.getTemperatureInCelsius();
    }

    // Endpoint to get temperature in Fahrenheit
    @Get("/temperature/fahrenheit")
    public double getTemperatureFahrenheit() {
        return thermistorHelper.getTemperatureInFahrenheit();
    }

    @Get("/rawValue")
    public double readADCValue(){
        return thermistorHelper.readADCValue();
    }
}