package com.opensourcewithslu.outputdevices;

import org.junit.jupiter.api.Test;

import org.slf4j.Logger;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.opensourcewithslu.outputdevices.SevenSegmentDisplayHelper;

public class FourDigitSevenSegmentDisplayHelperTest {
    FourDigitSevenSegmentDisplayHelper displayHelper = mock(FourDigitSevenSegmentDisplayHelper.class);
    SevenSegmentDisplayHelper sevenSegmentDisplayHelper = mock(SevenSegmentDisplayHelper.class);
    Logger log = mock(Logger.class);

    @Test
    void longNumberFails() {
        String number = "12345";
        displayHelper.displayNumber(number);
        verify(log).error("Number is too long");
        verify(log, never()).info("Displaying number: {}", "12345");
        verify(sevenSegmentDisplayHelper, never()).displayNumber(1);
        verify(sevenSegmentDisplayHelper, never()).displayNumber(2);
        verify(sevenSegmentDisplayHelper, never()).displayNumber(3);
        verify(sevenSegmentDisplayHelper, never()).displayNumber(4);
        verify(sevenSegmentDisplayHelper, never()).displayNumber(5);
    }

    void shortNumberFails() {
        String number = "";
        displayHelper.displayNumber(number);
        verify(log).error("Number is too short");
        verify(log, never()).info("Displaying number: {}", "");
    }

    @Test
    void nonNumberFails() {
        String number = "abc";
        displayHelper.displayNumber(number);
        verify(log).error("Display value must be an integer");
    }

    @Test
    void negativeNumberFails() {
        String number = "-1234";
        displayHelper.displayNumber(number);
        verify(log).error("Display value must be positive");
    }

    @Test
    void displaysFourDigitNumber() {
        String number = "1234";
        displayHelper.displayNumber(number);
        verify(displayHelper).displayNumber("1234");
        verify(log).info("Displaying number: {}", "1234");

        int displayed = displayHelper.getValue();
        assertEquals(1234, displayed);
    }

    @Test
    void displaysThreeDigitNumber() {
        String number = "123";
        displayHelper.displayNumber(number);
        verify(displayHelper).displayNumber("123");
        verify(log).info("Displaying number: {}", "123");

        int displayed = displayHelper.getValue();
        assertEquals(123, displayed);
    }

    @Test
    void displaysTwoDigitNumber() {
        String number = "34";
        displayHelper.displayNumber(number);
        verify(displayHelper).displayNumber("34");
        verify(log).info("Displaying number: {}", "34");

        int displayed = displayHelper.getValue();
        assertEquals(34, displayed);
    }

    @Test
    void displaysOneDigitNumber() {
        String number = "4";
        displayHelper.displayNumber(number);
        verify(displayHelper).displayNumber("4");
        verify(log).info("Displaying number: {}", "4");

        int displayed = displayHelper.getValue();
        assertEquals(4, displayed);
    }
}
