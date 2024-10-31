package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.pwm.Pwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.opensourcewithslu.utilities.MultiPinConfiguration;
import com.opensourcewithslu.outputdevices.SevenSegmentDisplayHelper;

public class FourDigitSevenSegmentDisplayHelper {
    private static Logger log = LoggerFactory.getLogger(FourDigitSevenSegmentDisplayHelper.class);

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

        sevenSegmentDisplay1 = new SevenSegmentDisplayHelper();
        sevenSegmentDisplay2 = new SevenSegmentDisplayHelper();
        sevenSegmentDisplay3 = new SevenSegmentDisplayHelper();
        sevenSegmentDisplay4 = new SevenSegmentDisplayHelper();
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
        try {
            int integerNumber = Integer.parseInt(number);
            if (integerNumber < 0) {
                log.error("Display value must be positive");
                return;
            }
        } catch (NumberFormatException e) {
            log.error("Display value must be an integer");
            return;
        }

        log.info("Displaying number: {}", number);

        resetDisplay();

        // Render numbers with leading zeroes, e.g. 1 as 0001
        switch (num.length) {
            case 1:
                sevenSegmentDisplay1.displayNumber(0);
                sevenSegmentDisplay2.displayNumber(0);
                sevenSegmentDisplay3.displayNumber(0);
                sevenSegmentDisplay4.displayNumber((int) num[0]);
                break;
            case 2:
                sevenSegmentDisplay1.displayNumber(0);
                sevenSegmentDisplay2.displayNumber(0);
                sevenSegmentDisplay3.displayNumber((int) num[0]);
                sevenSegmentDisplay4.displayNumber((int) num[1]);
                break;
            case 3:
                sevenSegmentDisplay1.displayNumber(0);
                sevenSegmentDisplay2.displayNumber((int) num[0]);
                sevenSegmentDisplay3.displayNumber((int) num[1]);
                sevenSegmentDisplay4.displayNumber((int) num[2]);
                break;
            case 4:
                sevenSegmentDisplay1.displayNumber((int) num[0]);
                sevenSegmentDisplay2.displayNumber((int) num[1]);
                sevenSegmentDisplay3.displayNumber((int) num[2]);
                sevenSegmentDisplay4.displayNumber((int) num[3]);
                break;
        }
    }

    //tag::method[]
    public void resetDisplay()
    //end::method[]
    {
        sevenSegmentDisplay1.resetDisplay();
        sevenSegmentDisplay2.resetDisplay();
        sevenSegmentDisplay3.resetDisplay();
        sevenSegmentDisplay4.resetDisplay();
    }

    //tag::method[]
    public int getValue()
    //end::method[]
    {
        int[] values = {sevenSegmentDisplay1.getValue(), sevenSegmentDisplay2.getValue(), sevenSegmentDisplay3.getValue(), sevenSegmentDisplay4.getValue()};
        return Integer.parseInt(String.valueOf(values));
    }
}
