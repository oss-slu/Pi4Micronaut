package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.FourDigitSevenSegmentDisplayHelper;
import com.opensourcewithslu.utilities.MultipinConfiguration;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2CConfig;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import com.pi4j.context.Context;
import jakarta.inject.Named;


//tag::ex[]

@Controller("/4digit7segment")
public class FourDigitSevenSegmentDisplayController {

    private final FourDigitSevenSegmentDisplayHelper fourDigitSevenSegmentDisplayHelper;

    public FourDigitSevenSegmentDisplayController(@Named("4digit7segment") MultipinConfiguration fourdigsevenseg, Context pi4jContext){
        this.fourDigitSevenSegmentDisplayHelper = new FourDigitSevenSegmentDisplayHelper(fourdigsevenseg, pi4jContext);
    }

    @Get("/test")
    public void test(){
        fourDigitSevenSegmentDisplayHelper.test();
    }

}
//end::ex[]
