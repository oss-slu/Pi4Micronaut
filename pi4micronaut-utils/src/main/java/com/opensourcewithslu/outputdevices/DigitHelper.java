package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.digital.DigitalOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DigitHelper {
    private static Logger log = LoggerFactory.getLogger(DigitHelper.class);

    private final DigitalOutput digitOutput;

    //tag::const[]
    public DigitHelper(DigitalOutput digitOutput)
    //end::const[]
    {
        this.digitOutput = digitOutput;
    }
}
