package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.ThermistorHelper;
import com.pi4j.io.spi.Spi;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

@Controller("/thermistor")
public class ThermistorController {

    private final ThermistorHelper thermistorHelper;

    /**
     * Constructor for ThermistorController.
     *
     * @param spi SPI interface
     */
    public ThermistorController(@Named("adc0834") Spi spi) {
        this.thermistorHelper = new ThermistorHelper(spi);
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