package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.FourDigitSevenSegmentDisplayHelper;
import com.opensourcewithslu.utilities.MultiPinConfiguration;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2CConfig;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

//tag::ex[]
@Controller("/4digit7segment")
public class FourDigitSevenSegmentDisplayController {
    private final FourDigitSevenSegmentDisplayHelper fourDigitSevenSegmentDisplayHelper;

    public FourDigitSevenSegmentDisplayController(@Named("4digit7segment") I2CConfig fourdigsevenseg, Context pi4jContext
    ) {
        this.fourDigitSevenSegmentDisplayHelper = new FourDigitSevenSegmentDisplayHelper(fourdigsevenseg, pi4jContext);
    }

    @Get("/displayNumber/{value}")
    public void displayValue(String value) {
        fourDigitSevenSegmentDisplayHelper.displayValue(value);
    }
}
//end::ex[]
