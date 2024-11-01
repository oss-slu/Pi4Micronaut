package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.ThermistorHelper;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

@Controller("/thermistor")
public class ThermistorController {
    private final ThermistorHelper thermistorHelper;

    public ThermistorController(@Named("thermistor-input") DigitalInput sensorInput, @Named("thermistor-output") DigitalOutput sensorOutput) {
        this.thermistorHelper = new ThermistorHelper(sensorInput, sensorOutput);
    }

    @Get("/celsius")
    public double getTemperatureCelsius() {
        return thermistorHelper.getTemperatureCelsius();
    }

    @Get("/fahrenheit")
    public double getTemperatureFahrenheit() {
        return thermistorHelper.getTemperatureFahrenheit();
    }
}

