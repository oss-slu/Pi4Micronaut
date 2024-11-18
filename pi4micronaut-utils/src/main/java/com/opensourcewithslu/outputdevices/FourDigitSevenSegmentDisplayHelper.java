package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.digital.DigitalOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

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
    private final int decimalPoint = 0x7f;

    /**
     * Mapping of characters to their respective byte representation.
     * Each byte is a bitset where each bit specifies if a specific segment should be disabled (1) or enabled (0).
     * Note carefully that the bits are inverted, so 0 means enabled and 1 means disabled.
     */
    protected static final Map<Character, Integer> CHAR_BITSETS = Map.ofEntries(
            Map.entry(' ', 0xff),
            Map.entry('-', 0x02),
            Map.entry('0', 0xc0),
            Map.entry('1', 0xf9),
            Map.entry('2', 0xa4),
            Map.entry('3', 0xb0),
            Map.entry('4', 0x99),
            Map.entry('5', 0x92),
            Map.entry('6', 0x82),
            Map.entry('7', 0xf8),
            Map.entry('8', 0x80),
            Map.entry('9', 0x90),
            Map.entry('A', 0x88),
            Map.entry('B', 0x83),
            Map.entry('C', 0xc6),
            Map.entry('D', 0xA1),
            Map.entry('E', 0x86),
            Map.entry('F', 0x8e)
    );

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

    public void shiftOut(Integer data, boolean decimalPointEnabled) {
        int value;
        for (int i = 0; i < 8; i++) { // Loop through each bit in the byte, one for each of the 7 segment and the decimal point
            if (decimalPointEnabled) {
                value = (data & (1 << (7 - i)) & this.decimalPoint);
            } else {
                value = (data & (1 << (7 - i)) | ~this.decimalPoint);
            }
            if (value != 0) {
                sdi.high(); // Disables segment
            } else {
                sdi.low(); // Enables segment
            }
            srclk.high();
            srclk.low();
        }
        rclk.high();
        rclk.low();
    }

    public void clear() {
        displayValue = "";
    }

    public void setDigit(int digit, char c, boolean decimalPoint) {
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

        // Lookup byte value for given character
        final var value = CHAR_BITSETS.get(Character.toUpperCase(c));
        if (value == null) {
            throw new IllegalArgumentException("Character is not supported by seven-segment display");
        }

//        log.info("previous value: " + numbers[Character.getNumericValue(c)] + " and now trying to do " + value);

        try {
//            shiftOut(numbers[Character.getNumericValue(c)], decimalPoint);
            shiftOut(value, decimalPoint);
        } catch (Exception e) {
            log.error(String.format("Error shifting out value %d to digit %d:", value, digit), e);
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
                        char value = displayValue.charAt(i);
                        setDigit(i, value, true);
                    } else {
                        setDigit(i, '0', false);
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
