package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.pwm.Pwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.opensourcewithslu.utilities.MultiPinConfiguration;

public class FourDigitSevenSegmentDisplayHelper {
    private static Logger log = LoggerFactory.getLogger(LEDHelper.class);

    private final DigitalOutput display1;
    private final DigitalOutput display2;
    private final DigitalOutput display3;
    private final DigitalOutput display4;

    //tag::const[]
    public FourDigitSevenSegmentDisplayHelper(MultiPinConfiguration fourdigitsevenseg)
    //end::const[]
    {
        DigitalOutput[] displays = (DigitalOutput[]) fourdigitsevenseg.getComponents();
        this.display1 = displays[0];
        this.display2 = displays[1];
        this.display3 = displays[2];
        this.display4 = displays[3];
    }
}
