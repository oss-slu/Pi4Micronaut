package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.digital.DigitalOutput;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

class LEDHelperTest {
    DigitalOutput ledOutput = mock(DigitalOutput.class);
    LEDHelper ledHelper = new LEDHelper(ledOutput);
    Logger log = mock(Logger.class);

    @BeforeEach
    public void openMocks() {
        ledHelper.setLog(log);
    }

    @Test
    void ledOnTurnsOnWhenOff() {
        when(ledOutput.isLow()).thenReturn(true);
        ledHelper.ledOn();
        verify(ledOutput).high();
    }

    @Test
    void ledOnDoesNotTurnOnWhenOn() {
        when(ledOutput.isLow()).thenReturn(false);
        ledHelper.ledOn();
        verify(ledOutput, never()).high();
    }

    @Test
    void ledOffTurnsOffWhenOn() {
        when(ledOutput.isHigh()).thenReturn(true);
        ledHelper.ledOff();
        verify(ledOutput).low();
    }

    @Test
    void ledOffDoesNotTurnOffWhenOff() {
        when(ledOutput.isHigh()).thenReturn(false);
        ledHelper.ledOff();
        verify(ledOutput, never()).low();
    }

    @Test
    void switchStateTogglesStateWhenOn() {
        when(ledOutput.isHigh()).thenReturn(true);
        ledHelper.switchState();
        verify(ledOutput).low();
    }

    @Test
    void switchStateTogglesStateWhenOff() {
        when(ledOutput.isHigh()).thenReturn(false);
        when(ledOutput.isLow()).thenReturn(true); // Called when switchState() calls ledOn()
        ledHelper.switchState();
        verify(ledOutput).high();
    }

    @Test
    void blinkCallsWithCorrectParameters() {
        int duration = 1000;
        ledHelper.blink(duration);
        verify(ledOutput).blink(duration, TimeUnit.MILLISECONDS);
    }

    @Test
    void ledOnLogsWhenTurnedOn() {
        when(ledOutput.isLow()).thenReturn(true);
        ledHelper.ledOn();
        verify(log).info("Turning on LED");
    }

    @Test
    void ledOffLogsWhenTurnedOff() {
        when(ledOutput.isHigh()).thenReturn(true);
        ledHelper.ledOff();
        verify(log).info("Turning off LED");
    }
}