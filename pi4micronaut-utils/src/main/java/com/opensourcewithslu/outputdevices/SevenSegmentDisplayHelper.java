package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.digital.DigitalOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class SevenSegmentDisplayHelper {
    private static Logger log = LoggerFactory.getLogger(SevenSegmentDisplayHelper.class);

    private final DigitalOutput pinA;
    private final DigitalOutput pinB;
    private final DigitalOutput pinC;
    private final DigitalOutput pinD;
    private final DigitalOutput pinE;
    private final DigitalOutput pinF;
    private final DigitalOutput pinG;
    private final DigitalOutput decimalPoint;

    private char digitValue;
    private boolean decimalPointEnabled = false;
    private boolean enabled = false;

    /**
     * Mapping of characters to their respective byte representation.
     * Each byte is a bitset where each bit specifies if a specific segment should be disabled (1) or enabled (0).
     * Note carefully that the bits are inverted, so 0 means enabled and 1 means disabled.
     */
    private static final Map<Character, Integer> CHAR_BITSETS = Map.ofEntries(
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
            Map.entry('D', 0xa1),
            Map.entry('E', 0x86),
            Map.entry('F', 0x8e)
    );

    /**
     * Constructor for the SevenSegmentDisplayHelper class.
     *
     * @param pinA Segment A
     * @param pinB Segment B
     * @param pinC Segment C
     * @param pinD Segment D
     * @param pinE Segment E
     * @param pinF Segment F
     * @param pinG Segment G
     * @param decimalPoint The decimal point
     */
    //tag::const[]
    public SevenSegmentDisplayHelper(DigitalOutput pinA, DigitalOutput pinB, DigitalOutput pinC, DigitalOutput pinD, DigitalOutput pinE, DigitalOutput pinF, DigitalOutput pinG, DigitalOutput decimalPoint)
    //end::const[]
    {
        this.pinA = pinA;
        this.pinB = pinB;
        this.pinC = pinC;
        this.pinD = pinD;
        this.pinE = pinE;
        this.pinF = pinF;
        this.pinG = pinG;
        this.decimalPoint = decimalPoint;

        this.digitValue = ' ';
    }

    /**
     * Helper method to start a second thread continuously updating the display.
     */
    private void startDisplayThread() {
        Thread displayThread = new Thread(() -> {
            while (enabled) {
                shiftValueToDigit(this.digitValue, this.decimalPointEnabled);
                try {
                    Thread.sleep(5); // Adjust the delay as needed
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error("Display thread interrupted", e);
                }
            }
        });
        displayThread.start();
    }

    /**
     * Helper method to shift out a byte to the shift register.
     *
     * @param data Data to shift out
     * @param decimalPointEnabled Whether the decimal point should be enabled or not
     */
    private void shiftOut(Integer data, boolean decimalPointEnabled) {
        int value;
        for (int i = 0; i < 8; i++) { // Loop through each bit in the byte, one for each of the 7 segment and the decimal point
            if (decimalPointEnabled) {
                value = (data & (1 << (7 - i)) & 0x7f);
            } else {
                value = (data & (1 << (7 - i)) | ~0x7f);
            }
            if (value != 0) {
                setPinHigh(i); // Disables segment
            } else {
                setPinLow(i); // Enables segment
            }
        }
    }

    /**
     * Helper method to set a specific pin high based on index.
     *
     * @param index The index of the pin to set high
     */
    private void setPinHigh(int index) {
        switch (index) {
            case 0 -> pinA.high();
            case 1 -> pinB.high();
            case 2 -> pinC.high();
            case 3 -> pinD.high();
            case 4 -> pinE.high();
            case 5 -> pinF.high();
            case 6 -> pinG.high();
            case 7 -> decimalPoint.high();
        }
    }

    /**
     * Helper method to set a specific pin low based on index.
     *
     * @param index The index of the pin to set low
     */
    private void setPinLow(int index) {
        switch (index) {
            case 0 -> pinA.low();
            case 1 -> pinB.low();
            case 2 -> pinC.low();
            case 3 -> pinD.low();
            case 4 -> pinE.low();
            case 5 -> pinF.low();
            case 6 -> pinG.low();
            case 7 -> decimalPoint.low();
        }
    }

    /**
     * Helper method to shift out a value to the digit.
     *
     * @param c The character to shift out
     * @param decimalPoint Whether the decimal point should be enabled or not
     */
    private void shiftValueToDigit(char c, boolean decimalPoint) {
        // Lookup byte value for given character
        final var value = CHAR_BITSETS.get(Character.toUpperCase(c));
        if (value == null) {
            throw new IllegalArgumentException("Character is not supported by seven-segment display");
        }

        try {
            shiftOut(value, decimalPoint);
        } catch (Exception e) {
            log.error(String.format("Error shifting out value %d for character %c:", value, c), e);
        }
    }

    /**
     * Clears the display.
     */
    //tag::method[]
    public void clear()
    //end::method[]
    {
        digitValue = ' ';
        this.shiftValueToDigit(digitValue, false);
        log.info("Display cleared");
    }

    /**
     * Enables the display.
     */
    //tag::method[]
    public void enable()
    //end::method[]
    {
        this.enabled = true;
        this.startDisplayThread();
        log.info("Display enabled");
    }

    /**
     * Disables the display.
     */
    //tag::method[]
    public void disable()
    //end::method[]
    {
        this.clear();
        this.enabled = false;
        log.info("Display disabled");
    }

    /**
     * Displays a value on the seven-segment display.
     *
     * @param value The value to display. It can include digits 0-9, letters A-F (case-insensitive),
     *              hyphens, spaces, and decimal points.
     */
    //tag::method[]
    public void print(char value, boolean decimalPointEnabled)
    //end::method[]
    {
        if (!enabled) {
            log.error("Display must be enabled first");
            return;
        }

        // Validate character
        if (!CHAR_BITSETS.containsKey(Character.toUpperCase(value))) {
            log.error("Character is not supported by seven-segment display");
            return;
        }

        this.digitValue = value;
        this.decimalPointEnabled = decimalPointEnabled;
        this.shiftValueToDigit(digitValue, decimalPointEnabled);
        log.info("Displaying value: {}", this.digitValue);
    }
}
