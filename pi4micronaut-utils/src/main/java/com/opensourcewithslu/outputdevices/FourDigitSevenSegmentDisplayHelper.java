package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.digital.DigitalOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FourDigitSevenSegmentDisplayHelper {
    private static final Logger log = LoggerFactory.getLogger(FourDigitSevenSegmentDisplayHelper.class);

    private final DigitalOutput sdi;
    private final DigitalOutput rclk;
    private final DigitalOutput srclk;
    private final DigitalOutput digit0;
    private final DigitalOutput digit1;
    private final DigitalOutput digit2;
    private final DigitalOutput digit3;

    private boolean enabled = false;
    private int[] numbers = {0xc0, 0xf9, 0xa4, 0xb0, 0x99, 0x92, 0x82, 0xf8, 0x80, 0x90};
    private String displayValue;

    public FourDigitSevenSegmentDisplayHelper(DigitalOutput sdi, DigitalOutput rclk, DigitalOutput srclk, DigitalOutput digit0, DigitalOutput digit1, DigitalOutput digit2, DigitalOutput digit3) {
        this.sdi = sdi;
        this.rclk = rclk;
        this.srclk = srclk;
        this.digit0 = digit0;
        this.digit1 = digit1;
        this.digit2 = digit2;
        this.digit3 = digit3;

        this.displayValue = "";
    }

    public void shiftOut(int data, boolean decimalPoint) {
        try {
            for (int i = 0; i < 8; i++) {
                if (i == 0 && decimalPoint) {
                    sdi.high();
                } else if ((data & (1 << (7 - i))) != 0) {
                    sdi.high();
                } else {
                    sdi.low();
                }
//            sdi.setState(0x80 & (data << i));
                srclk.high();
                srclk.low();
            }
            rclk.high();
            rclk.low();
        } catch (Exception e) {
            log.error("Error shifting out data", e);
        }
    }

    public void clear() {
        displayValue = "";
    }

    public void setDigit(int digit, int value, boolean decimalPoint) {
        digit0.low();
        digit1.low();
        digit2.low();
        digit3.low();

        switch (digit) {
            case 0:
                digit0.high();
                break;
            case 1:
                digit1.high();
                break;
            case 2:
                digit2.high();
                break;
            case 3:
                digit3.high();
                break;
        }

        try {
            shiftOut(numbers[value], decimalPoint);
        } catch (Exception e) {
            log.error(String.format("Error shifting out value %d to digit %d", value, digit), e);
        }
    }

    public void enable() {
        this.enabled = true;
        this.startDisplayThread();
    }

    public void disable() {
        this.clear();
        this.enabled = false;
    }

    public void displayValue(String value) {
        this.displayValue = value;
        log.info("Displaying value: " + displayValue);
    }

    public void startDisplayThread() {
        Thread displayThread = new Thread(() -> {
            while (enabled) {
                for (int i = 0; i < 4; i++) {
                    if (i < displayValue.length()) {
                        int value = Character.getNumericValue(displayValue.charAt(i));
                        setDigit(i, value, true);
                    } else {
                        setDigit(i, 0, false);
                    }
                    try {
                        Thread.sleep(5); // Adjust the delay as needed
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        log.error("Display thread interrupted", e);
                    }
                }
            }
        });
        displayThread.start();
    }
}
