package com.opensourcewithslu.outputdevices;

import com.pi4j.io.exception.IOException;
import com.pi4j.io.pwm.Pwm;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServoMotorHelperTest {
    Logger log = mock(Logger.class);
    Pwm servoMotor = mock(Pwm.class);
    ServoMotorHelper servoMotorHelper = new ServoMotorHelper(servoMotor);

    private static final int FREQUENCY = 50; // Frequency for PWM signal in Hz, typical for servo motors.
    private static final int PWM_CYCLE_MICROSECONDS = 20000; // Total cycle length in microseconds, corresponding to 50 Hz.

    @BeforeEach
    public void openMocks() {
        servoMotorHelper.setLog(log);
    }

    @Test
    void enables() {
        try {
            servoMotorHelper.enable();
            verify(servoMotor).on(0, 50);
        } catch (IOException e) {
            throw new RuntimeException("Error running test: ", e);
        }
        verify(log).info("Enabling servo motor");
        assertTrue(servoMotorHelper.isEnabled());
    }

    @Test
    void disables() {
        try {
            servoMotorHelper.enable();
            servoMotorHelper.disable();
        } catch (Exception e) {
            throw new RuntimeException("Error running test: ", e);
        }
        verify(servoMotor).off();
        verify(log).info("Disabling servo motor");
        assertFalse(servoMotorHelper.isEnabled());
    }

    @Test
    void setAngleFailsWhenDisabled() {
        try {
            servoMotorHelper.setAngle(90);
        } catch (Exception e) {
            throw new RuntimeException("Error running test: ", e);
        }
        verify(log).info("You must enable the servo motor first.");
        verify(servoMotor, never()).on(0, 50);
        verify(log, never()).info("Setting angle to {} degrees", 90);
    }

    @Test
    void setAngleLowFails() {
        try {
            servoMotorHelper.enable();
            servoMotorHelper.setAngle(-10);
        } catch (Exception e) {
            throw new RuntimeException("Error running test: ", e);
        }
        verify(log).info("You must enter an angle between 0 and 180 degrees.");

        float pulseWidth = servoMotorHelper.map(-10);
        float dutyCycle = (pulseWidth / PWM_CYCLE_MICROSECONDS) * 100;

        verify(servoMotor, never()).on(dutyCycle, FREQUENCY);
        verify(log, never()).info("Setting angle to {} degrees", -10);
    }

    @Test
    void setAngleHighFails() {
        try {
            servoMotorHelper.enable();
            servoMotorHelper.setAngle(181);
        } catch (Exception e) {
            throw new RuntimeException("Error running test: ", e);
        }
        verify(log).info("You must enter an angle between 0 and 180 degrees.");

        float pulseWidth = servoMotorHelper.map(181);
        float dutyCycle = (pulseWidth / PWM_CYCLE_MICROSECONDS) * 100;

        verify(servoMotor, never()).on(dutyCycle, FREQUENCY);
        verify(log, never()).info("Setting angle to {} degrees", 181);
    }

    @Test
    void setAngleAtMinimumValue() {
        try {
            servoMotorHelper.enable();
            servoMotorHelper.setAngle(0);
        } catch (Exception e) {
            throw new RuntimeException("Error running test: ", e);
        }

        float pulseWidth = servoMotorHelper.map(0);
        float dutyCycle = (pulseWidth / PWM_CYCLE_MICROSECONDS) * 100;

        verify(log).info("Setting servo to {} degrees, Pulse Width: {} us, Duty Cycle: {}%", 0, pulseWidth, dutyCycle);
        verify(servoMotor).on(dutyCycle, FREQUENCY);
    }

    @Test
    void setAngleAtMaximumValue() {
        try {
            servoMotorHelper.enable();
        } catch (Exception e) {
            throw new RuntimeException("Error running test: ", e);
        }

        float pulseWidth = servoMotorHelper.map(180);
        float dutyCycle = (pulseWidth / PWM_CYCLE_MICROSECONDS) * 100;

        try {
            servoMotorHelper.setAngle(180);
        } catch (Exception e) {
            throw new RuntimeException("Error running test: ", e);
        }
        verify(log).info("Setting servo to {} degrees, Pulse Width: {} us, Duty Cycle: {}%", 180, pulseWidth, dutyCycle);
        verify(servoMotor).on(dutyCycle, FREQUENCY);
    }

    @Test
    void setAngle() {
        try {
            servoMotorHelper.enable();
        } catch (Exception e) {
            throw new RuntimeException("Error running test: ", e);
        }

        float pulseWidth = servoMotorHelper.map(90);
        float dutyCycle = (pulseWidth / PWM_CYCLE_MICROSECONDS) * 100;

        try {
            servoMotorHelper.setAngle(90);
        } catch (Exception e) {
            throw new RuntimeException("Error running test: ", e);
        }
        verify(log).info("Setting servo to {} degrees, Pulse Width: {} us, Duty Cycle: {}%", 90, pulseWidth, dutyCycle);
        verify(servoMotor).on(dutyCycle, FREQUENCY);
    }

    @Test
    void map() {
        assertEquals(500, servoMotorHelper.map(0));
        assertEquals(2500, servoMotorHelper.map(180));
        assertEquals(1500, servoMotorHelper.map(90));
    }
}
