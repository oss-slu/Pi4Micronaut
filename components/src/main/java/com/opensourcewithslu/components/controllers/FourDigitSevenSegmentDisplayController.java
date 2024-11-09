package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.DigitHelper;
//import com.opensourcewithslu.utilities.MultiPinConfiguration;
import com.opensourcewithslu.outputdevices.SegmentHelper;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

//tag::ex[]
@Controller("/four-digit-seven-segment")
public class FourDigitSevenSegmentDisplayController {
    //    private final FourDigitSevenSegmentDisplayHelper fourDigitSevenSegmentDisplayHelper;
    private final SegmentHelper segmentA;
    private final SegmentHelper segmentB;
    private final SegmentHelper segmentC;
    private final SegmentHelper segmentD;
    private final SegmentHelper segmentE;
    private final SegmentHelper segmentF;
    private final SegmentHelper segmentG;
    private final SegmentHelper segmentDot;
    private final DigitHelper digit0;
    private final DigitHelper digit1;
    private final DigitHelper digit2;
    private final DigitHelper digit3;

    public FourDigitSevenSegmentDisplayController(@Named("segment-a") DigitalOutput segmentA,
                                                  @Named("segment-b") DigitalOutput segmentB,
                                                  @Named("segment-c") DigitalOutput segmentC,
                                                  @Named("segment-d") DigitalOutput segmentD,
                                                  @Named("segment-e") DigitalOutput segmentE,
                                                  @Named("segment-f") DigitalOutput segmentF,
                                                  @Named("segment-g") DigitalOutput segmentG,
                                                  @Named("segment-dot") DigitalOutput segmentDot,
                                                  @Named("digit-0") DigitalOutput digit0,
                                                  @Named("digit-1") DigitalOutput digit1,
                                                  @Named("digit-2") DigitalOutput digit2,
                                                  @Named("digit-3") DigitalOutput digit3
    ) {
//        this.fourDigitSevenSegmentDisplayHelper = new FourDigitSevenSegmentDisplayHelper(segmentA, segmentB, segmentC, segmentD, segmentE, segmentF, segmentG, segmentDot, digit0, digit1, digit2, digit3);
//        this.fourDigitSevenSegmentDisplayHelper = new FourDigitSevenSegmentDisplayHelper(fourdigsevenseg);
        this.segmentA = new SegmentHelper(segmentA);
        this.segmentB = new SegmentHelper(segmentB);
        this.segmentC = new SegmentHelper(segmentC);
        this.segmentD = new SegmentHelper(segmentD);
        this.segmentE = new SegmentHelper(segmentE);
        this.segmentF = new SegmentHelper(segmentF);
        this.segmentG = new SegmentHelper(segmentG);
        this.segmentDot = new SegmentHelper(segmentDot);
        this.digit0 = new DigitHelper(digit0);
        this.digit1 = new DigitHelper(digit1);
        this.digit2 = new DigitHelper(digit2);
        this.digit3 = new DigitHelper(digit3);
    }

    /*@Get("/displayNumber/{value}")
    public void displayValue(String value) {
        fourDigitSevenSegmentDisplayHelper.displayValue(value);
    }*/

    @Get("/test")
    public void test() {
        segmentA.segmentOn();
    }
}
//end::ex[]
