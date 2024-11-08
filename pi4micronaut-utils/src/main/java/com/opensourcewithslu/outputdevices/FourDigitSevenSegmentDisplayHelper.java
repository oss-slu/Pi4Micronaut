package com.opensourcewithslu.outputdevices;

import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.util.Map;

import java.util.Arrays;

public class FourDigitSevenSegmentDisplayHelper {
    private static final Logger log = LoggerFactory.getLogger(FourDigitSevenSegmentDisplayHelper.class);

    private final I2C i2c;

    //tag::const[]
    public FourDigitSevenSegmentDisplayHelper(I2CConfig i2cConfig, Context pi4jContext)
    //end::const[]
    {
        this.i2c = pi4jContext.create(i2cConfig);
    }


}
