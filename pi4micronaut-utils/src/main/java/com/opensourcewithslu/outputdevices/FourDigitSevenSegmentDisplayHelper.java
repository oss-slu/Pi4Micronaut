package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.pwm.Pwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import com.opensourcewithslu.utilities.MultiPinConfiguration;

public class FourDigitSevenSegmentDisplayHelper {
/*
    private static Logger log = LoggerFactory.getLogger(FourDigitSevenSegmentDisplayHelper.class);

*/
/*    private int[][] charMap = {
            {1, 1, 1, 1, 1, 1, 0},
            {0, 1, 1, 0, 0, 0, 0},
            {1, 1, 0, 1, 1, 0, 1},
            {1, 1, 1, 1, 0, 0, 1},
            {0, 1, 1, 0, 0, 1, 1},
            {1, 0, 1, 1, 0, 1, 1},
            {1, 0, 1, 1, 1, 1, 1},
            {1, 1, 1, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 0, 1, 1},
            {0, 0, 0, 0, 0, 0, 1}
    };*/
    /*


    private enum CharMap {
        ZERO(new int[]{1, 1, 1, 1, 1, 1, 0}),
        ONE(new int[]{0, 1, 1, 0, 0, 0, 0}),
        TWO(new int[]{1, 1, 0, 1, 1, 0, 1}),
        THREE(new int[]{1, 1, 1, 1, 0, 0, 1}),
        FOUR(new int[]{0, 1, 1, 0, 0, 1, 1}),
        FIVE(new int[]{1, 0, 1, 1, 0, 1, 1}),
        SIX(new int[]{1, 0, 1, 1, 1, 1, 1}),
        SEVEN(new int[]{1, 1, 1, 0, 0, 0, 0}),
        EIGHT(new int[]{1, 1, 1, 1, 1, 1, 1}),
        NINE(new int[]{1, 1, 1, 1, 0, 1, 1}),
        HYPHEN(new int[]{0, 0, 0, 0, 0, 0, 1});

        private int[] segmentArray;

        public int[] getSegmentArray() {
            return this.segmentArray;
        }

        CharMap(int[] segmentArray) {
            this.segmentArray = segmentArray;
        }
    }

    private final DigitalOutput segmentA;
    private final DigitalOutput segmentB;
    private final DigitalOutput segmentC;
    private final DigitalOutput segmentD;
    private final DigitalOutput segmentE;
    private final DigitalOutput segmentF;
    private final DigitalOutput segmentG;
    private final DigitalOutput DP;
    private final DigitalOutput digit0;
    private final DigitalOutput digit1;
    private final DigitalOutput digit2;
    private final DigitalOutput digit3;
*/

    //tag::const[]
    public FourDigitSevenSegmentDisplayHelper(/*DigitalOutput segmentA, DigitalOutput segmentB, DigitalOutput segmentC, DigitalOutput segmentD, DigitalOutput segmentE, DigitalOutput segmentF, DigitalOutput segmentG, DigitalOutput segmentDot, DigitalOutput digit0, DigitalOutput digit1, DigitalOutput digit2, DigitalOutput digit3*/)
    //end::const[]
    {
        /*DigitalOutput[] components = (DigitalOutput[]) fourDigitSevenSeg.getComponents();
        this.segmentA = components[0];
        this.segmentB = components[1];
        this.segmentC = components[2];
        this.segmentD = components[3];
        this.segmentE = components[4];
        this.segmentF = components[5];
        this.segmentG = components[6];
        this.DP = components[7];
        this.digit0 = components[8];
        this.digit1 = components[9];
        this.digit2 = components[10];
        this.digit3 = components[11];*/
/*        this.segmentA = segmentA;
        this.segmentB = segmentB;
        this.segmentC = segmentC;
        this.segmentD = segmentD;
        this.segmentE = segmentE;
        this.segmentF = segmentF;
        this.segmentG = segmentG;
        this.DP = segmentDot;
        this.digit0 = digit0;
        this.digit1 = digit1;
        this.digit2 = digit2;
        this.digit3 = digit3;*/
    }

    //tag::method[]
/*    public void displayValue(String value)
    //end::method[]
    {
        char[] num = value.toCharArray();

        if (num.length > 4) {
            log.error("Number is too long");
            return;
        }
        if (num.length == 0) {
            log.error("Number is too short");
            return;
        }
        try {
            int integerNumber = Integer.parseInt(value);
            if (integerNumber < 0) {
                log.error("Display value must be positive");
                return;
            }
        } catch (NumberFormatException e) {
            log.error("Display value must be an integer");
            return;
        }

        log.info("Displaying number: {}", value);

        // TODO: replace with actual implementation
        setValue(digit0, CharMap.FOUR);*/

        // Render numbers with leading zeroes, e.g. 1 as 0001
/*        switch (num.length) {
            case 1:
                */
    /*sevenSegmentDisplay1.displayNumber(0);
                sevenSegmentDisplay2.displayNumber(0);
                sevenSegmentDisplay3.displayNumber(0);
                sevenSegmentDisplay4.displayNumber((int) num[0]);*/
    /*
                break;
            case 2:
                */
    /*sevenSegmentDisplay1.displayNumber(0);
                sevenSegmentDisplay2.displayNumber(0);
                sevenSegmentDisplay3.displayNumber((int) num[0]);
                sevenSegmentDisplay4.displayNumber((int) num[1]);*/
    /*
                break;
            case 3:
                */
    /*sevenSegmentDisplay1.displayNumber(0);
                sevenSegmentDisplay2.displayNumber((int) num[0]);
                sevenSegmentDisplay3.displayNumber((int) num[1]);
                sevenSegmentDisplay4.displayNumber((int) num[2]);*/
    /*
                break;
            case 4:
                */
    /*sevenSegmentDisplay1.displayNumber((int) num[0]);
                sevenSegmentDisplay2.displayNumber((int) num[1]);
                sevenSegmentDisplay3.displayNumber((int) num[2]);
                sevenSegmentDisplay4.displayNumber((int) num[3]);*/
    /*
                break;
        }
    }*/

    /*//tag::method[]
    public String getValue()
    //end::method[]
    {
        */
    /*int[] values = {sevenSegmentDisplay1.getValue(), sevenSegmentDisplay2.getValue(), sevenSegmentDisplay3.getValue(), sevenSegmentDisplay4.getValue()};
        return Integer.parseInt(String.valueOf(values));*/
    /*
    }*/

    /*  private void setValue(DigitalOutput digit, CharMap character) {
     */
    /*segmentA.off();
        segmentB.off();
        segmentC.off();
        segmentD.off();
        segmentE.off();
        segmentF.off();
        segmentG.off();

        digit.on(100, 50);
        for (int i = 0; i < 7; i++) {
            if (character.getSegmentArray()[i] == 1) {
                switch (i) {
                    case 0:
                        segmentA.on(100, 50);
                        break;
                    case 1:
                        segmentB.on(100, 50);
                        break;
                    case 2:
                        segmentC.on(100, 50);
                        break;
                    case 3:
                        segmentD.on(100, 50);
                        break;
                    case 4:
                        segmentE.on(100, 50);
                        break;
                    case 5:
                        segmentF.on(100, 50);
                        break;
                    case 6:
                        segmentG.on(100, 50);
                        break;
                }
            }
        }*/
    /*

        segmentA.low();
        segmentB.low();
        segmentC.low();
        segmentD.low();
        segmentE.low();
        segmentF.low();
        segmentG.low();

        digit.high();
        for (int i = 0; i < 7; i++) {
            if (character.getSegmentArray()[i] == 1) {
                switch (i) {
                    case 0:
                        segmentA.high();
                        break;
                    case 1:
                        segmentB.high();
                        break;
                    case 2:
                        segmentC.high();
                        break;
                    case 3:
                        segmentD.high();
                        break;
                    case 4:
                        segmentE.high();
                        break;
                    case 5:
                        segmentF.high();
                        break;
                    case 6:
                        segmentG.high();
                        break;
                }
            }
        }
    }*/
}
