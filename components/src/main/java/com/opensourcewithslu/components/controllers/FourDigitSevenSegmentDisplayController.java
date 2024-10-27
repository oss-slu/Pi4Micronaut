package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.FourDigitSevenSegmentDisplayHelper;
import com.opensourcewithslu.utilities.MultiPinConfiguration;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

//tag::ex[]
@Controller("/4digit7segment")
public class FourDigitSevenSegmentDisplayController {
    private final FourDigitSevenSegmentDisplayHelper fourDigitSevenSegmentDisplayHelper;

    public FourDigitSevenSegmentDisplayController(@Named("4digit7segment") MultiPinConfiguration fourdigsevenseg) {
        this.fourDigitSevenSegmentDisplayHelper = new FourDigitSevenSegmentDisplayHelper(fourdigsevenseg);
    }
}
//end::ex[]
