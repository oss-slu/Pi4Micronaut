package com.opensourcewithslu.outputdevices;

import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FourDigitSevenSegmentDisplayHelper {
    private static final Logger log = LoggerFactory.getLogger(FourDigitSevenSegmentDisplayHelper.class);

    private final I2C i2c;

    private enum CharMap {
        ZERO(0x3F),
        ONE(0x06),
        TWO(0x5B),
        THREE(0x4F),
        FOUR(0x66),
        FIVE(0x6D),
        SIX(0x7D),
        SEVEN(0x07),
        EIGHT(0x7F),
        NINE(0x6F),
        HYPHEN(0x40);

        private final int segmentValue;

        CharMap(int segmentValue) {
            this.segmentValue = segmentValue;
        }

        public int getSegmentValue() {
            return segmentValue;
        }
    }

    public FourDigitSevenSegmentDisplayHelper(I2CConfig i2cConfig, Context pi4jContext) {
        this.i2c = pi4jContext.create(i2cConfig);
    }

    public void displayValue(String value) {
        if (value.length() > 4) {
            log.error("Number is too long");
            return;
        }
        if (value.length() == 0) {
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

        for (int i = 0; i < value.length(); i++) {
            CharMap charMap = CharMap.valueOf(String.valueOf(value.charAt(i)));
            setValue(i, charMap);
        }
    }

    private void setValue(int digit, CharMap character) {
        int segmentValue = character.getSegmentValue();
        i2c.writeRegister(digit, (byte) segmentValue);
    }
}
