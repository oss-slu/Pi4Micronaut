package com.opensourcewithslu.outputdevices;

import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2CConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pi4j.crowpi.components.SevenSegmentComponent;

public class FourDigitSevenSegmentDisplayHelper {
    private static final Logger log = LoggerFactory.getLogger(FourDigitSevenSegmentDisplayHelper.class);
    private final SevenSegmentComponent display;

    //tag::const[]
    public FourDigitSevenSegmentDisplayHelper(I2CConfig i2cConfig, Context pi4jContext)
    //end::const[]
    {
        this.display = new SevenSegmentComponent(pi4jContext, i2cConfig.getBus(), i2cConfig.getDevice());
    }

    public void setEnabled(boolean enabled) {
        display.setEnabled(enabled);
    }

    public void displayValue(String value) {
        /* Validate value. Must satisfy following properties:
            - No more than 4 non-decimal point characters long
            - No consecutive decimal points
            - If there are 4 non-decimal point characters, then decimal points must not appear on the ends
            - Non-decimal point characters must be digits 0 to 1, letters A to F (case-insensitive), -, or space
         */

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
        String valid = "1234567890ABCDEF- ";
        for (char character : noDecimals.toCharArray()) {
            if (valid.indexOf(character) == -1) {
                log.error("Each display value digit must be numeric, a letter A to F, a hyphen, or a space");
                return;
            }
        }

        display.print(value);
        display.refresh();
    }

    public void clear() {
        for (int i = 0; i < 4; i++) {
            display.clear();
            display.setDecimalPoint(i, false);
        }
        display.setColon(false);
        display.refresh();
    }


}
