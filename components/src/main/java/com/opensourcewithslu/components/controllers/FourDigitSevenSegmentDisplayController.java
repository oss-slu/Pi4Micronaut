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

    @Get("/print/{value}")
    public void print(String value) {
        displayHelper.print(value);
    }

    @Get("/clear")
    public void clear() {
        displayHelper.clear();
    }

    @Get("set-digit/{digit}/{value}")
    public void setDigit(int digit, char value) {
        displayHelper.setDigit(digit, value);
    }

    @Get("set-decimal-point/{digit}/{value}")
    public void setDecimalPoint(int digit, boolean value) {
        displayHelper.setDecimalPoint(digit, value);
    }
}
//end::ex[]
