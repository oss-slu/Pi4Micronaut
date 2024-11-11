package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.ADCConverterHelper;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ADCConverterController provides an endpoint to read values from the ADC0834.
 */
@Controller("/adcConverter")
public class ADCConverterController {
    private static final Logger log = LoggerFactory.getLogger(ADCConverterController.class);
    private final ADCConverterHelper adcConverterHelper;

    /**
     * Constructor for ADCConverterController.
     *
     * @param cs  DigitalOutput for Chip Select
     * @param clk DigitalOutput for Clock
     * @param din DigitalOutput for Data In
     * @param dout DigitalInput for Data Out
     */
    public ADCConverterController(@Named("cs") DigitalOutput cs,
                                  @Named("clk") DigitalOutput clk,
                                  @Named("din") DigitalOutput din,
                                  @Named("dout") DigitalInput dout) {
        this.adcConverterHelper = new ADCConverterHelper(cs, clk, din, dout);
    }

    /**
     * Endpoint to get the digital value from ADC0834.
     *
     * @return The digital value read from the ADC0834.
     */
    @Get("/read")
    public int readValue() {
        int value = adcConverterHelper.readValue();
        log.info("Value retrieved from ADC0834: {}", value);
        return value;
    }
}
