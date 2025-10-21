package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.pwm.Pwm;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;

public class MotorHelperTest {
    DigitalOutput pin1 = mock(DigitalOutput.class);
    DigitalOutput pin2 = mock(DigitalOutput.class);
    Pwm motor = mock(Pwm.class);
    MotorHelper motorHelper = new MotorHelper(motor, pin1, pin2);
    Logger log = mock(Logger.class);

    @BeforeEach
    public void openMocks() {
        motorHelper.setLog(log);
    }

    @Test
    void enables() {
        motorHelper.enable();
        verify(motor).on(0, 50);
        verify(log).info("Enabling DC motor");
    }

    @Test
    void disables() {
        motorHelper.disable();
        verify(motor).off();
        verify(log).info("Disabling DC motor");
    }

    @Test
    void setSpeedWorksWhenEnabled() {
        motorHelper.enable();
        verify(log).info("Enabling DC motor");
        motorHelper.setSpeed(10);
        verify(motor).on(10.0d, 50);
        verify(log).info("Setting motor speed to {}%", 10.0d);
    }

    @Test
    void setSpeedFailsWhenDisabled() {
        motorHelper.setSpeed(10);
        verify(log).info("You must enable the DC motor first.");
        verify(motor, never()).on(10.0d, 50);
        verify(log, never()).info("Setting motor speed to {}%", 10);
    }

    @Test
    void setSpeedFailsWhenSpeedIsNegative() {
        motorHelper.enable();
        verify(log).info("Enabling DC motor");
        motorHelper.setSpeed(-10);
        verify(log).info("You must enter a speed between 0 and 100.");
        verify(motor, never()).on(-10.0d, 50);
        verify(log, never()).info("Setting speed to {}%", -10.0d);
    }

    @Test
    void setSpeedFailsWhenSpeedIsAbove100() {
        motorHelper.enable();
        verify(log).info("Enabling DC motor");
        motorHelper.setSpeed(110);
        verify(log).info("You must enter a speed between 0 and 100.");
        verify(motor, never()).on(110.0d, 50);
        verify(log, never()).info("Setting motor speed to {}%", 110.0d);
    }

    @Test
    void setClockwiseTrueWorksWhenEnabled() {
        motorHelper.enable();
        verify(log).info("Enabling DC motor");
        motorHelper.setClockwise(true);
        verify(pin1).high();
        verify(pin2).low();
        verify(log).info("Setting motor direction clockwise to {}", true);
    }

    @Test
    void setClockwiseFalseWorksWhenEnabled() {
        motorHelper.enable();
        verify(log).info("Enabling DC motor");
        motorHelper.setClockwise(false);
        verify(pin1).low();
        verify(pin2).high();
        verify(log).info("Setting motor direction clockwise to {}", false);
    }

    @Test
    void setClockwiseFailsWhenDisabled() {
        motorHelper.setClockwise(true);
        verify(log).info("You must enable the DC motor first.");
        verify(pin1, never()).high();
        verify(pin2, never()).low();
        verify(log, never()).info("Setting motor direction clockwise to {}", true);
    }

    @Test
    void switchDirectionFromClockwiseWorksWhenEnabled() {
        motorHelper.enable();
        verify(log).info("Enabling DC motor");
        motorHelper.setClockwise(true);
        motorHelper.switchDirection();
        verify(pin1).low();
        verify(pin2).high();
        verify(log).info("Setting motor direction clockwise to {}", false);
    }

    @Test
    void switchDirectionToClockwiseWorksWhenEnabled() {
        motorHelper.enable();
        verify(log).info("Enabling DC motor");
        motorHelper.setClockwise(false);
        motorHelper.switchDirection();
        verify(pin1).high();
        verify(pin2).low();
        verify(log).info("Setting motor direction clockwise to {}", false);
    }

    @Test
    void switchDirectionFailsWhenDisabled() {
        motorHelper.switchDirection();
        verify(log).info("You must enable the DC motor first.");
        verify(pin1, never()).high();
        verify(pin1, never()).low();
        verify(pin2, never()).high();
        verify(pin2, never()).low();
        verify(log, never()).info("Setting motor direction clockwise to {}", true);
        verify(log, never()).info("Setting motor direction clockwise to {}", false);
    }
}
