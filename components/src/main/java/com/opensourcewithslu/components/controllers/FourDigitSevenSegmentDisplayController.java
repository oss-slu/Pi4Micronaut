package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.FourDigitSevenSegmentDisplayHelper;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

//tag::ex[]
@Controller("/four-digit-seven-segment")
public class FourDigitSevenSegmentDisplayController {
    private final FourDigitSevenSegmentDisplayHelper displayHelper;

    public FourDigitSevenSegmentDisplayController(@Named("sdi") DigitalOutput sdi,
                                                  @Named("rclk") DigitalOutput rclk,
                                                  @Named("srclk") DigitalOutput srclk,
                                                  @Named("digit-0") DigitalOutput digit0,
                                                  @Named("digit-1") DigitalOutput digit1,
                                                  @Named("digit-2") DigitalOutput digit2,
                                                  @Named("digit-3") DigitalOutput digit3) {
        this.displayHelper = new FourDigitSevenSegmentDisplayHelper(sdi, rclk, srclk, digit0, digit1, digit2, digit3);
    }

    @Get("/enable")
    public void enable() {
        displayHelper.enable();
    }

    @Get("/disable")
    public void disable() {
        displayHelper.disable();
    }

    @Get("/displayValue/{value}")
    public void displayValue(String value) {
        displayHelper.displayValue(value);
    }

    @Get("/clear")
    public void clearDisplay() {
        displayHelper.clear();
    }
}
//end::ex[]
