package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.FourDigitSevenSegmentDisplayHelper;
import com.opensourcewithslu.utilities.MultiPinConfiguration;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

//tag::ex[]
@Controller("/four-digit-seven-segment")
public class FourDigitSevenSegmentDisplayController {
    private final FourDigitSevenSegmentDisplayHelper fourDigitSevenSegmentDisplayHelper;

    public FourDigitSevenSegmentDisplayController(@Named("four-digit-seven-segment") MultiPinConfiguration fourdigsevenseg
    ) {
        this.fourDigitSevenSegmentDisplayHelper = new FourDigitSevenSegmentDisplayHelper(fourdigsevenseg);
    }

    @Get("/displayNumber/{value}")
    public void displayValue(String value) {
        fourDigitSevenSegmentDisplayHelper.displayValue(value);
    }
}
//end::ex[]
