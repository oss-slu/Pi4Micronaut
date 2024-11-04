package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.FourDigitSevenSegmentDisplayHelper;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2CConfig;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import com.pi4j.crowpi.components.internal.HT16K33;

//tag::ex[]
@Controller("/4digit7segment")
public class FourDigitSevenSegmentDisplayController {
    private final FourDigitSevenSegmentDisplayHelper fourDigitSevenSegmentDisplayHelper;

    @Inject
    public FourDigitSevenSegmentDisplayController(@Named("4digit7segment") I2CConfig fourdigsevenseg, Context pi4jContext
    ) {
        this.fourDigitSevenSegmentDisplayHelper = new FourDigitSevenSegmentDisplayHelper(fourdigsevenseg, pi4jContext);
    }

    @Get("/enable")
    public void enable() {
        fourDigitSevenSegmentDisplayHelper.enableDisplay(true);
    }

    @Get("/displayNumber/{value}")
    public void displayValue(String value) {
        fourDigitSevenSegmentDisplayHelper.print(value);
    }

    @Get("/disable")
    public void disable() {
        fourDigitSevenSegmentDisplayHelper.setEnabled(false);
    }
}
//end::ex[]
