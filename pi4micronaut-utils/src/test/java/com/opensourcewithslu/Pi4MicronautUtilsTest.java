package com.opensourcewithslu;

import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.runtime.EmbeddedApplication;
//import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import jakarta.inject.Inject;
import static org.mockito.Mockito.*;
import java.util.concurrent.TimeUnit;
import com.opensourcewithslu.outputdevices.LEDHelper;

// @MicronautTest
class Pi4MicronautUtilsTest {
/*   @Inject EmbeddedApplication<?> application;

   @Test
   void testItWorks() {
       Assertions.assertTrue(application.isRunning());
   }*/

    DigitalOutput ledOutput = mock(DigitalOutput.class);
    LEDHelper ledHelper = new LEDHelper(ledOutput);

    @Test
    void ledOn_turnsOnWhenCurrentlyOff() {
        when(ledOutput.isLow()).thenReturn(true);
        ledHelper.ledOn();
        verify(ledOutput).high();
    }

    @Test
    void ledOn_doesNotTurnOnWhenAlreadyOn() {
        when(ledOutput.isLow()).thenReturn(false);
        ledHelper.ledOn();
        verify(ledOutput, never()).high();
    }

    @Test
    void ledOff_turnsOffWhenCurrentlyOn() {
        when(ledOutput.isHigh()).thenReturn(true);
        ledHelper.ledOff();
        verify(ledOutput).low();
    }

    @Test
    void ledOff_doesNotTurnOffWhenAlreadyOff() {
        when(ledOutput.isHigh()).thenReturn(false);
        ledHelper.ledOff();
        verify(ledOutput, never()).low();
    }

    @Test
    void switchState_togglesStateWhenCurrentlyOn() {
        when(ledOutput.isHigh()).thenReturn(true);
        ledHelper.switchState();
        verify(ledOutput).low();
    }

    @Test
    void switchState_togglesStateWhenCurrentlyOff() {
        when(ledOutput.isHigh()).thenReturn(false);
        when(ledOutput.isLow()).thenReturn(true); // Called when switchState() calls ledOn()
        ledHelper.switchState();
        verify(ledOutput).high();
    }

    @Test
    void blink_callsBlinkWithCorrectParameters() {
        int duration = 1000;
        ledHelper.blink(duration);
        verify(ledOutput).blink(duration, TimeUnit.MILLISECONDS);
    }
}
