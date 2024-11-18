package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.digital.DigitalOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @BeforeEach
    public void openMocks() {
        displayHelper.setLog(log);
    }

    @Test
    void longNumberIsCutOff() {
        String value = "12345";
        displayHelper.print(value);
        verify(log).info("Displaying value: {}", "1234");
    }

    @Test
    void consecutiveDecimalPointIsParsed() {
        String value = "1..23";
        displayHelper.print(value);
        String myValue = " ";
        assertEquals(' ', myValue.charAt(0));
        verify(log).info("Displaying value: {}", "1. .23");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("1. .23", displayed);
    }

    @Test
    void multipleConsecutiveDecimalPointIsParsed() {
        String value = "1...3";
        displayHelper.print(value);
        verify(log).info("Displaying value: {}", "1. . .3");
        String displayed = displayHelper.getDisplayValue();
        assertEquals("1. . .3", displayed);
    }

    @Test
    void decimalPointOnLeftEndCutsOff() {
        String value = ".1234";
        displayHelper.print(value);
        verify(log).info("Displaying value: {}", " .123");
        String displayed = displayHelper.getDisplayValue();
        assertEquals(" .123", displayed);
    }

    @Test
    void invalidCharacterFails() {
        String value = "G";
        displayHelper.print(value);
        verify(log).error("Each display value digit must be numeric, a letter A to F (case insensitive), a hyphen, or a space");
        verify(log, never()).info("Displaying value: {}", "G");
    }

    @Test
    void displaysLetters() {
        String value = "ABCD";
        displayHelper.print(value);
        verify(log).info("Displaying value: {}", "ABCD");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("ABCD", displayed);
    }

    @Test
    void displaysLettersWithLowercase() {
        String value = "abcd";
        displayHelper.print(value);
        verify(log).info("Displaying value: {}", "ABCD");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("ABCD", displayed);
    }

    @Test
    void displaysLettersWithMixedCase() {
        String value = "aBcD";
        displayHelper.print(value);
        verify(log).info("Displaying value: {}", "ABCD");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("ABCD", displayed);
    }

    @Test
    void displaysHyphen() {
        String value = "-";
        displayHelper.print(value);
        verify(log).info("Displaying value: {}", "-   ");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("-   ", displayed);
    }

    @Test
    void displaysSpaces() {
        String value = "2 2";
        displayHelper.print(value);
        verify(log).info("Displaying value: {}", "2 2 ");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("2 2 ", displayed);
    }

    @Test
    void displaysNegativeNumber() {
        String number = "-123";
        displayHelper.print(number);
        verify(log).info("Displaying value: {}", "-123");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("-123", displayed);
    }

    @Test
    void displaysFourDigitNumber() {
        String number = "1234";
        displayHelper.print(number);
        verify(log).info("Displaying value: {}", "1234");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("1234", displayed);
    }

    @Test
    void displaysThreeDigitNumber() {
        String number = "123";
        displayHelper.print(number);
        verify(log).info("Displaying value: {}", "123 ");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("123 ", displayed);
    }

    @Test
    void displaysTwoDigitNumber() {
        String number = "34";
        displayHelper.print(number);
        verify(log).info("Displaying value: {}", "34  ");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("34  ", displayed);
    }

    @Test
    void displaysOneDigitNumber() {
        String number = "4";
        displayHelper.print(number);
        verify(log).info("Displaying value: {}", "4   ");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("4   ", displayed);
    }

    @Test
    void displaysBlankValue() {
        String number = "";
        displayHelper.print(number);
        verify(log).info("Displaying value: {}", "    ");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("    ", displayed);
    }

    @Test
    void displaysDecimalNumber() {
        String number = "1.23";
        displayHelper.print(number);
        verify(log).info("Displaying value: {}", "1.23 ");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("1.23 ", displayed);
    }

    @Test
    void displaysDecimalNumberWithLeadingDecimal() {
        String number = ".23";
        displayHelper.print(number);
        verify(log).info("Displaying value: {}", " .23 ");

        String displayed = displayHelper.getDisplayValue();
        assertEquals(" .23 ", displayed);
    }

    @Test
    void displaysDecimalNumberWithTrailingDecimal() {
        String number = "1.";
        displayHelper.print(number);
        verify(log).info("Displaying value: {}", "1.   ");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("1.   ", displayed);
    }

    @Test
    void displaysMultipleDecimals() {
        String number = "1.2.3.4.";
        displayHelper.print(number);
        verify(log).info("Displaying value: {}", "1.2.3.4.");

        String displayed = displayHelper.getDisplayValue();
        assertEquals("1.2.3.4.", displayed);
    }

    @Test
    void displaysSpacesAndDecimals() {
        String number = " . . . ";
        displayHelper.print(number);
        verify(log).info("Displaying value: {}", " . . . ");

        String displayed = displayHelper.getDisplayValue();
        assertEquals(" . . . ", displayed);
    }

    @Test
    void clearDisplay() {
        String number = "1234";
        displayHelper.print(number);

        displayHelper.clear();

        String displayed = displayHelper.getDisplayValue();
        assertEquals("    ", displayed);
    }

    /* TODO: Add tests for setDigit and setDecimalPoint */
}
