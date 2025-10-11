package com.opensourcewithslu.components.controllers;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.spi.Spi;
import com.pi4j.io.spi.SpiConfig;
import com.pi4j.io.spi.SpiConfigBuilder;
import com.pi4j.io.spi.SpiChipSelect; // Import SpiChipSelect
import com.opensourcewithslu.inputdevices.ADC0834ConverterHelper;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ADCConverterController provides an endpoint to read values from the ADC0834.
 */
@Controller("/adcConverter")
public class ADC0834ConverterController {
    private static final Logger log = LoggerFactory.getLogger(ADC0834ConverterController.class);
    private final ADC0834ConverterHelper adcConverterHelper;

    /**
     * Constructor for ADCConverterController.
     *
     * @param pi4j Pi4J Context for SPI communication
     */
    public ADC0834ConverterController(Context pi4j) {
        // Configure SPI
        SpiConfig spiConfig = SpiConfigBuilder.newInstance(pi4j)
                .id("adc0834-spi")
                .address(0) // SPI bus 0
                .chipSelect(SpiChipSelect.CS_0) // Use SpiChipSelect enum for chip select 0
                .baud(1000000) // Set appropriate baud rate (e.g., 1 MHz, adjust as needed)
                .build();

        Spi spi = pi4j.create(spiConfig); // Create SPI instance
        this.adcConverterHelper = new ADC0834ConverterHelper(spi); // Pass SPI instance to helper
        log.info("ADCConverterController initialized with SPI");
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
