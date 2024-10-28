package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.FourDigitSevenSegmentDisplayHelper;
import com.opensourcewithslu.utilities.MultiPinConfiguration;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.Digital;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

//tag::ex[]
@Controller("/4digit7segment")
public class FourDigitSevenSegmentDisplayController {
    private final FourDigitSevenSegmentDisplayHelper fourDigitSevenSegmentDisplayHelper;

    public FourDigitSevenSegmentDisplayController(@Named("4digit7segment") MultiPinConfiguration fourdigsevenseg,
                                                  @Named("display1") DigitalOutput display1,
                                                  @Named("display1") DigitalOutput display2,
                                                  @Named("display1") DigitalOutput display3,
                                                  @Named("display1") DigitalOutput display4
    ) {
        DigitalOutput[] displayList = {display1, display2, display3, display4};
        this.fourDigitSevenSegmentDisplayHelper = new FourDigitSevenSegmentDisplayHelper(fourdigsevenseg, displayList);
    }

    @Get("/displayNumber/{number}")
    public void displayNumber(String number) {
        fourDigitSevenSegmentDisplayHelper.displayNumber(number);
    }
}
//end::ex[]
