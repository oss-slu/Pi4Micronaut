package com.opensourcewithslu.outputdevices;

import com.pi4j.context.ContextProperties;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.i2c.I2CConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.pi4j.context.Context;
import com.pi4j.crowpi.components.SevenSegmentComponent;

import java.lang.reflect.Field;

public class FourDigitSevenSegmentDisplayHelperTest {
    DigitalOutput sdi = mock(DigitalOutput.class);
    DigitalOutput rclk = mock(DigitalOutput.class);
    DigitalOutput srclk = mock(DigitalOutput.class);
    DigitalOutput digit0 = mock(DigitalOutput.class);
    DigitalOutput digit1 = mock(DigitalOutput.class);
    DigitalOutput digit2 = mock(DigitalOutput.class);
    DigitalOutput digit3 = mock(DigitalOutput.class);
    FourDigitSevenSegmentDisplayHelper displayHelper = new FourDigitSevenSegmentDisplayHelper(sdi, rclk, srclk, digit0, digit1, digit2, digit3);
    Logger log = mock(Logger.class);

    /*@BeforeEach
    void setUp() throws Exception {
        // Mock the Context to return a non-null ContextProperties
        when(pi4jContext.properties()).thenReturn(contextProperties);

        displayHelper = new FourDigitSevenSegmentDisplayHelper(i2cConfig, pi4jContext);

        // Use reflection to set the mock SevenSegmentComponent
        Field displayField = FourDigitSevenSegmentDisplayHelper.class.getDeclaredField("display");
        displayField.setAccessible(true);
        displayField.set(displayHelper, displayComponent);
    }*/

    @BeforeEach
    public void openMocks() {
        displayHelper.setLog(log);
    }

    @Test
    void longNumberFails() {
        String value = "12345";
        displayHelper.displayValue(value);
        verify(log).error("Display value must not have more than 4 non-decimal point characters");
        verify(log, never()).info("Displaying value: {}", "12345");
    }

    @Test
    void consecutiveDecimalPointsFails() {
        String value = "1..23";
        displayHelper.displayValue(value);
        verify(log).error("Display value cannot have consecutive decimal points");
        verify(log, never()).info("Displaying value: {}", "1..23");
    }

    @Test
    void decimalPointOnLeftEndFails() {
        String value = ".1234";
        displayHelper.displayValue(value);
        verify(log).error("Display value must have decimal points appearing strictly between the digits");
        verify(log, never()).info("Displaying value: {}", ".1234");
    }

    @Test
    void decimalPointOnRightEndFails() {
        String value = "1234.";
        displayHelper.displayValue(value);
        verify(log).error("Display value must have decimal points appearing strictly between the digits");
        verify(log, never()).info("Displaying value: {}", "1234.");
    }

    @Test
    void decimalPointsOnBothEndsFails() {
        String value = ".1234.";
        displayHelper.displayValue(value);
        verify(log).error("Display value must have decimal points appearing strictly between the digits");
        verify(log, never()).info("Displaying value: {}", ".1234.");
    }

    @Test
    void invalidCharacterFails() {
        String value = "G";
        displayHelper.displayValue(value);
        verify(log).error("Each display value digit must be numeric, a letter A to F (case insensitive), a hyphen, or a space");
        verify(log, never()).info("Displaying value: {}", "G");
    }

    @Test
    void displaysLetters() {
        String value = "ABCD";
        displayHelper.displayValue(value);
        verify(log).info("Displaying value: {}", "ABCD");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("ABCD", displayed);
    }

    @Test
    void displaysLettersWithLowercase() {
        String value = "abcd";
        displayHelper.displayValue(value);
        verify(log).info("Displaying value: {}", "ABCD");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("ABCD", displayed);
    }

    @Test
    void displaysLettersWithMixedCase() {
        String value = "aBcD";
        displayHelper.displayValue(value);
        verify(log).info("Displaying value: {}", "ABCD");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("ABCD", displayed);
    }

    @Test
    void displaysHyphen() {
        String value = "-";
        displayHelper.displayValue(value);
        verify(log).info("Displaying value: {}", "-");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("-", displayed);
    }

    @Test
    void displaysSpaces() {
        String value = "2 2";
        displayHelper.displayValue(value);
        verify(log).info("Displaying value: {}", "2 2");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("2 2", displayed);
    }

    @Test
    void displaysNegativeNumber() {
        String number = "-123";
        displayHelper.displayValue(number);
        verify(log).info("Displaying value: {}", "-123");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("-123", displayed);
    }

    @Test
    void displaysFourDigitNumber() {
        String number = "1234";
        displayHelper.displayValue(number);
        verify(log).info("Displaying value: {}", "1234");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("1234", displayed);
    }

    @Test
    void displaysThreeDigitNumber() {
        String number = "123";
        displayHelper.displayValue(number);
        verify(log).info("Displaying value: {}", "123");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("123", displayed);
    }

    @Test
    void displaysTwoDigitNumber() {
        String number = "34";
        displayHelper.displayValue(number);
        verify(log).info("Displaying value: {}", "34");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("34", displayed);
    }

    @Test
    void displaysOneDigitNumber() {
        String number = "4";
        displayHelper.displayValue(number);
        verify(log).info("Displaying value: {}", "4");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("4", displayed);
    }

    @Test
    void displaysBlankValue() {
        String number = "";
        displayHelper.displayValue(number);
        verify(log).info("Displaying value: {}", "");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("", displayed);
    }

    @Test
    void displaysDecimalNumber() {
        String number = "1.23";
        displayHelper.displayValue(number);
        verify(log).info("Displaying value: {}", "1.23");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("1.23", displayed);
    }

    @Test
    void displaysDecimalNumberWithLeadingDecimal() {
        String number = ".23";
        displayHelper.displayValue(number);
        verify(log).info("Displaying value: {}", ".23");

        String displayed = displayHelper.getDisplayValue();
        assertEquals(".23", displayed);
    }

    @Test
    void displaysDecimalNumberWithTrailingDecimal() {
        String number = "1.";
        displayHelper.displayValue(number);
        verify(log).info("Displaying value: {}", "1.");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("1.", displayed);
    }

    @Test
    void displaysMultipleDecimals() {
        String number = "1.2.3.4";
        displayHelper.displayValue(number);
        verify(log).info("Displaying value: {}", "1.2.3.4");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("1.2.3.4", displayed);
    }

    @Test
    void displaysSpacesAndDecimals() {
        String number = " . . . ";
        displayHelper.displayValue(number);
        verify(log).info("Displaying value: {}", " . . . ");

        String displayed = displayHelper.getDisplayValue();
        assertEquals(" . . . ", displayed);
    }

    @Test
    void clearDisplay() {
        String number = "1234";
        displayHelper.displayValue(number);

        displayHelper.clear();

        String displayed = displayHelper.getDisplayValue();
        assertEquals("", displayed);
    }
}
