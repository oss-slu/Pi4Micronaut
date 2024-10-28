package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.pwm.Pwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.opensourcewithslu.utilities.MultiPinConfiguration;
import com.opensourcewithslu.outputdevices.SevenSegmentDisplayHelper;

public class FourDigitSevenSegmentDisplayHelper {
    private static Logger log = LoggerFactory.getLogger(LEDHelper.class);

    private final DigitalOutput display1;
    private final DigitalOutput display2;
    private final DigitalOutput display3;
    private final DigitalOutput display4;

    private final SevenSegmentDisplayHelper sevenSegmentDisplay1;
    private final SevenSegmentDisplayHelper sevenSegmentDisplay2;
    private final SevenSegmentDisplayHelper sevenSegmentDisplay3;
    private final SevenSegmentDisplayHelper sevenSegmentDisplay4;

    //tag::const[]
    public FourDigitSevenSegmentDisplayHelper(MultiPinConfiguration fourdigitsevenseg, DigitalOutput[] displayList)
    //end::const[]
    {
        DigitalOutput[] displays = (DigitalOutput[]) fourdigitsevenseg.getComponents();
        this.display1 = displays[0];
        this.display2 = displays[1];
        this.display3 = displays[2];
        this.display4 = displays[3];

        sevenSegmentDisplay1 = new SevenSegmentDisplayHelper(displayList[0]);
        sevenSegmentDisplay2 = new SevenSegmentDisplayHelper(displayList[1]);
        sevenSegmentDisplay3 = new SevenSegmentDisplayHelper(displayList[2]);
        sevenSegmentDisplay4 = new SevenSegmentDisplayHelper(displayList[3]);
    }

    //tag::method[]
    public void displayNumber(String number)
    //end::method[]
    {
        char[] num = number.toCharArray();

        if (num.length > 4) {
            log.error("Number is too long");
            return;
        }
        if (num.length == 0) {
            log.error("Number is too short");
            return;
        }

        log.info("Displaying number: " + number);

        display2.low();
        display3.low();
        display4.low();

        display1.high();
        sevenSegmentDisplay1.displayNumber(num[0]);
        if (num.length > 1) {
            display2.high();
            sevenSegmentDisplay2.disaplNumber(num[1]);
        }
        if (num.length > 2) {
            display3.high();
            sevenSegmentDisplay2.disaplNumber(num[2]);
        }
        if (num.length > 3) {
            display4.high();
            sevenSegmentDisplay2.disaplNumber(num[3]);
        }
    }
}
