package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.ADC0834ConverterHelper;
import com.pi4j.context.Context;
import com.pi4j.io.spi.SpiConfig;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ADC0834ConverterController provides an endpoint to read values from the ADC0834.
 */
@Controller("/adc0834")
public class ADC0834ConverterController {
    private static final Logger log = LoggerFactory.getLogger(ADC0834ConverterController.class);
    private final ADC0834ConverterHelper adcConverterHelper;

    /**
     * Constructor for ADC0834ConverterController.
     *
     * @param spiConfig A Pi4J SPIConfig object which holds the SPI configuration.
     * @param pi4jContext The Pi4J context object.
     */
    public ADC0834ConverterController(@Named("adc0834") SpiConfig spiConfig, Context pi4jContext) {
        this.adcConverterHelper = new ADC0834ConverterHelper(spiConfig, pi4jContext);
        log.info("ADC0834ConverterController initialized with SPI");
    }

    /**
     * Endpoint to get the digital value from the specified ADC0834 channel.
     *
     * @param channel The ADC channel to read (0-3).
     * @return The digital value read from the ADC0834.
     */
    @Get("/read/{channel}")
    public int readValue(int channel) {
        int value = adcConverterHelper.readValue(channel);
        log.info("Value retrieved from ADC0834: {}", value);
        return value;
    }

    /**
     * Endpoint to get the voltage from the specified ADC0834 channel.
     *
     * @param channel The ADC channel to read (0-3).
     * @param referenceVoltage The reference voltage for the ADC.
     * @return The voltage value read from the ADC0834.
     */
    @Get("/voltage/{channel}/{referenceVoltage}")
    public double readVoltage(int channel, double referenceVoltage) {
        double voltage = adcConverterHelper.readVoltage(channel, referenceVoltage);
        log.info("Voltage retrieved from ADC0834 channel {}: {}V", channel, voltage);
        return voltage;
    }
}
