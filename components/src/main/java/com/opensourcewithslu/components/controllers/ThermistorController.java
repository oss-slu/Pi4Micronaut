package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.ADC0834ConverterHelper;
import com.opensourcewithslu.inputdevices.ThermistorHelper;
import com.pi4j.context.Context;
import com.pi4j.io.spi.SpiConfig;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

@Controller("/thermistor")
public class ThermistorController {

    private final ThermistorHelper thermistorHelper;
    private final ADC0834ConverterHelper adcConverterHelper;

    /**
     * Constructor for ThermistorController.
     *
     * @param spiConfig A Pi4J SPIConfig object which holds the SPI configuration for the ADC0834.
     * @param pi4jContext The Pi4J context object.
     */
    public ThermistorController(@Named("adc0834") SpiConfig spiConfig, Context pi4jContext) {
        adcConverterHelper = new ADC0834ConverterHelper(spiConfig, pi4jContext);
        thermistorHelper = new ThermistorHelper( adcConverterHelper );
    }

    // Endpoint to get temperature in Celsius
    @Get("/temperature/celsius/{channel}")
    public double getTemperatureCelsius(int channel) {
        return thermistorHelper.getTemperatureInCelsius( channel );
    }

    // Endpoint to get temperature in Fahrenheit
    @Get("/temperature/fahrenheit/{channel}")
    public double getTemperatureFahrenheit(int channel) {
        return thermistorHelper.getTemperatureInFahrenheit( channel );
    }

    @Get("/rawValue/{channel}")
    public double readADCValue(int channel){
        return thermistorHelper.readADCValue( channel );
    }
}