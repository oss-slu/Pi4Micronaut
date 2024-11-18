package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.digital.DigitalOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class FourDigitSevenSegmentDisplayHelper {
    private static Logger log = LoggerFactory.getLogger(FourDigitSevenSegmentDisplayHelper.class);

    private final DigitalOutput sdi;
    private final DigitalOutput rclk;
    private final DigitalOutput srclk;
    private final DigitalOutput digit0;
    private final DigitalOutput digit1;
    private final DigitalOutput digit2;
    private final DigitalOutput digit3;

    private String displayValue;
    private boolean enabled = false;
    private final int decimalPoint = 0x7f;

    private char[] digitValues = new char[4];
    private boolean[] decimalPoints = new boolean[4];

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
            Map.entry('D', 0xA1),
            Map.entry('E', 0x86),
            Map.entry('F', 0x8e)
    );

    /**
     * Constructor for the FourDigitSevenSegmentDisplayHelper class.
     *
     * @param sdi    Serial data input
     * @param rclk   Register clock
     * @param srclk  Shift register clock
     * @param digit0 The first digit
     * @param digit1 The second digit
     * @param digit2 The third digit
     * @param digit3 The fourth digit
     */
    //tag::const[]
    public FourDigitSevenSegmentDisplayHelper(DigitalOutput sdi, DigitalOutput rclk, DigitalOutput srclk, DigitalOutput digit0, DigitalOutput digit1, DigitalOutput digit2, DigitalOutput digit3)
    //end::const[]
    {
        this.sdi = sdi;
        this.rclk = rclk;
        this.srclk = srclk;
        this.digit0 = digit0;
        this.digit1 = digit1;
        this.digit2 = digit2;
        this.digit3 = digit3;

        this.displayValue = "    ";
        this.parseDisplayValue();
    }

    /**
     * Helper method to start a second thread continuously updating the display.
     */
    private void startDisplayThread() {
        Thread displayThread = new Thread(() -> {
            while (enabled) {
                for (int i = 0; i < 4; i++) {
                    shiftValueToDigit(i, this.digitValues[i], this.decimalPoints[i]);
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

    /**
     * Helper method to shift out a byte to the shift register.
     *
     * @param data
     * @param decimalPointEnabled
     */
    private void shiftOut(Integer data, boolean decimalPointEnabled) {
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

    /**
     * Helper method to shift out a value to a specific digit.
     *
     * @param digit
     * @param c
     * @param decimalPoint
     */
    private void shiftValueToDigit(int digit, char c, boolean decimalPoint) {
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

        try {
            shiftOut(value, decimalPoint);
        } catch (Exception e) {
            log.error(String.format("Error shifting out value %d to digit %d:", value, digit), e);
        }
    }

    /**
     * Helper method to parse the display value into digit values and decimal points.
     */
    private void parseDisplayValue() {
        this.digitValues = new char[]{' ', ' ', ' ', ' '};
        this.decimalPoints = new boolean[]{false, false, false, false};

        // Add leading space if the first character is a decimal point
        if (!this.displayValue.isEmpty() && this.displayValue.charAt(0) == '.') {
            this.displayValue = " " + this.displayValue;
        }

        // Replace consecutive decimal points with a space in between
        while (true) {
            this.displayValue = this.displayValue.replaceAll("\\.\\.", ". .");
            // If parsing again would result in no change, we're done and can break
            if (this.displayValue.replaceAll("\\.\\.", ". .").equals(this.displayValue)) {
                break;
            }
        }

        int idx = 0, pos = 0;
        while (idx < this.displayValue.length() && pos < 4) {
            // Set digit to character at current index and advance
            this.digitValues[pos] = this.displayValue.charAt(idx++);

            // Exit early if we reached the end
            if (idx >= this.displayValue.length()) {
                break;
            }

            // Set decimal point if next character is dot
            if (this.displayValue.charAt(idx) == '.') {
                this.decimalPoints[pos] = true;
                idx++;
            }

            // Exit early if we reached the end
            if (idx >= this.displayValue.length()) {
                break;
            }

            // Advance to next digit
            pos++;
        }

        this.setDisplayValueFromDigitValues();
    }

    /**
     * Helper method to set the display value from the digit values and decimal points.
     */
    private void setDisplayValueFromDigitValues() {
        this.displayValue = "";
        for (int i = 0; i < 4; i++) {
            this.displayValue += digitValues[i];
            if (decimalPoints[i]) {
                this.displayValue += ".";
            }
        }
    }

    /**
     * Clears the display.
     */
    //tag::method[]
    public void clear()
    //end::method[]
    {
        displayValue = "";
        this.parseDisplayValue();
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
    }

    /**
     * Displays a value on the four-digit seven-segment display.
     *
     * @param value The value to display. It can include digits 0-9, letters A-F (case-insensitive),
     *              hyphens, spaces, and decimal points.
     */
    //tag::method[]
    public void print(String value)
    //end::method[]
    {
        // Check: Non-decimal point characters must be digits 0 to 1, letters A to F (case-insensitive), -, or space
        String noDecimals = value.replaceAll("\\.", "");
        String valid = "1234567890ABCDEFabcdef- ";
        for (char character : noDecimals.toCharArray()) {
            if (valid.indexOf(character) == -1) {
                log.error("Each display value digit must be numeric, a letter A to F (case insensitive), a hyphen, or a space");
                return;
            }
        }

        value = value.toUpperCase();

        this.displayValue = value;
        this.parseDisplayValue();
        log.info("Displaying value: {}", this.displayValue);
    }

    /**
     * Sets the logger object.
     *
     * @param log Logger object to set the logger to.
     */
    //tag::method[]
    public void setLog(Logger log)
    //end::method[]
    {
        FourDigitSevenSegmentDisplayHelper.log = log;
    }

    /**
     * Gets the display value.
     *
     * @return The display value
     */
    //tag::method[]
    public String getDisplayValue()
    //end::method[]
    {
        return displayValue;
    }

    /**
     * Sets the value of a digit.
     *
     * @param digit The digit to set the value of (0-3)
     * @param value The value to set the digit to. Must be a digit 0-9, a letter A-F (case-insensitive), a hyphen, or a space
     */
    //tag::method[]
    public void setDigit(int digit, char value)
    //end::method[]
    {
        if (digit < 0 || digit > 3) {
            throw new IllegalArgumentException("Digit must be between 0 and 3");
        }
        String valid = "1234567890ABCDEFabcdef- ";
        if (valid.indexOf(value) == -1) {
            log.error("Each display value digit must be numeric, a letter A to F (case insensitive), a hyphen, or a space");
            return;
        }
        digitValues[digit] = value;
        this.setDisplayValueFromDigitValues();
    }

    /**
     * Sets the decimal point of a digit.
     *
     * @param digit   The digit to set the decimal point of (0-3)
     * @param enabled Whether the decimal point should be enabled or not
     */
    //tag::method[]
    public void setDecimalPoint(int digit, boolean enabled)
    //end::method[]
    {
        if (digit < 0 || digit > 3) {
            throw new IllegalArgumentException("Digit must be between 0 and 3");
        }
        decimalPoints[digit] = enabled;
        this.setDisplayValueFromDigitValues();
    }
}
