package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.DigitHelper;
import com.opensourcewithslu.outputdevices.SegmentHelper;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.spi.SpiConfig;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

@Controller("/four-digit-seven-segment")
public class FourDigitSevenSegmentDisplayController {
    private final SegmentHelper segmentHelper;
    private final DigitHelper digit0;
    private final DigitHelper digit1;
    private final DigitHelper digit2;
    private final DigitHelper digit3;

    private static final int[] number = {0xc0, 0xf9, 0xa4, 0xb0, 0x99, 0x92, 0x82, 0xf8, 0x80, 0x90};
    private int counter = 0;

    public FourDigitSevenSegmentDisplayController(@Named("four-digit-seven-segment") SpiConfig spi,
                                                  @Named("sdi") DigitalOutput sdi,
                                                  @Named("rclk") DigitalOutput rclk,
                                                  @Named("srclk") DigitalOutput srclk,
                                                  @Named("digit-0") DigitalOutput digit0,
                                                  @Named("digit-1") DigitalOutput digit1,
                                                  @Named("digit-2") DigitalOutput digit2,
                                                  @Named("digit-3") DigitalOutput digit3) {
        this.segmentHelper = new SegmentHelper(sdi, rclk, srclk);
        this.digit0 = new DigitHelper(digit0);
        this.digit1 = new DigitHelper(digit1);
        this.digit2 = new DigitHelper(digit2);
        this.digit3 = new DigitHelper(digit3);
    }

    private void pickDigit(int digit) {
        digit0.digitOff();
        digit1.digitOff();
        digit2.digitOff();
        digit3.digitOff();

        switch (digit) {
            case 0:
                digit0.digitOn();
                break;
            case 1:
                digit1.digitOn();
                break;
            case 2:
                digit2.digitOn();
                break;
            case 3:
                digit3.digitOn();
                break;
        }
    }

    private void hc595_shift(int data) {
        segmentHelper.shiftOut(data);
    }

    private void clearDisplay() {
        segmentHelper.clear();
    }

    @Get("/test")
    public void test() {
        while (true) {
            clearDisplay();
            pickDigit(0);
            hc595_shift(number[counter % 10]);

            clearDisplay();
            pickDigit(1);
            hc595_shift(number[counter % 100 / 10]);

            clearDisplay();
            pickDigit(2);
            hc595_shift(number[counter % 1000 / 100]);

            clearDisplay();
            pickDigit(3);
            hc595_shift(number[counter % 10000 / 1000]);
        }
    }
}
