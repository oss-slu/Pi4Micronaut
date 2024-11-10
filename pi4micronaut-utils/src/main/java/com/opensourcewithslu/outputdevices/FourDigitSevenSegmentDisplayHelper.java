package com.opensourcewithslu.outputdevices;

import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2CConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pi4j.crowpi.components.SevenSegmentComponent;

public class FourDigitSevenSegmentDisplayHelper {
    private static Logger log = LoggerFactory.getLogger(FourDigitSevenSegmentDisplayHelper.class);
    private final SevenSegmentComponent display;
    private String displayValue;

    /**
     * Constructs a new FourDigitSevenSegmentDisplayHelper.
     *
     * @param i2cConfig   the I2C configuration
     * @param pi4jContext the Pi4J context
     */
    //tag::const[]
    public FourDigitSevenSegmentDisplayHelper(I2CConfig i2cConfig, Context pi4jContext)
    //end::const[]
    {
        this.display = new SevenSegmentComponent(pi4jContext, i2cConfig.getBus(), i2cConfig.getDevice());
    }

    /**
     * Enables or disables the display.
     *
     * @param enabled True to enable, false to disable
     */
    //tag::method[]
    public void setEnabled(boolean enabled)
    //end::method[]
    {
        display.setEnabled(enabled);
        this.displayValue = "";
    }

    /**
     * Displays a value on the four-digit seven-segment display.
     *
     * @param value The value to display. It can include digits 0-9, letters A-F (case-insensitive),
     *              hyphens, spaces, and decimal points. The value must not have more than 4 non-decimal
     *              point characters, no consecutive decimal points, and if there are 4 non-decimal
     *              point characters, decimal points must not appear on the ends.
     */
    //tag::method[]
    public void displayValue(String value)
    //end::method[]
    {
        // Parse out the decimal points
        String noDecimals = value.replaceAll("\\.", "");

        // Check: No more than 4 non-decimal point characters long
        if (noDecimals.length() > 4) {
            log.error("Display value must not have more than 4 non-decimal point characters");
            return;
        }

        // Check: No consecutive decimal points
        if (value.contains("..")) {
            log.error("Display value cannot have consecutive decimal points");
            return;
        }

        // Check: If there are 4 non-decimal point characters, then decimal points must not appear on the ends
        if (noDecimals.length() == 4 && (value.startsWith(".") || value.endsWith("."))) {
            log.error("Display value must have decimal points appearing strictly between the digits");
            return;
        }

        // Check: Non-decimal point characters must be digits 0 to 1, letters A to F (case-insensitive), -, or space
        String valid = "1234567890ABCDEFabcdef- ";
        for (char character : noDecimals.toCharArray()) {
            if (valid.indexOf(character) == -1) {
                log.error("Each display value digit must be numeric, a letter A to F (case insensitive), a hyphen, or a space");
                return;
            }
        }

        display.print(value);
        display.refresh();
        value = value.toUpperCase();
        log.info("Displaying value: {}", value);
        this.displayValue = value;
    }

    /**
     * Clears the display.
     */
    //tag::method[]
    public void clear()
    //end::method[]
    {
        for (int i = 0; i < 4; i++) {
            display.clear();
            display.setDecimalPoint(i, false);
        }
        display.setColon(false);
        display.refresh();
        this.displayValue = "";
    }

    /**
     * Sets the logger object.
     *
     * @param log Logger object to set the logger to.
     */
    public void setLog(Logger log) {
        FourDigitSevenSegmentDisplayHelper.log = log;
    }

    /**
     * Gets the display value.
     *
     * @return The display value
     */
    public String getDisplayValue() {
        return displayValue;
    }
}
