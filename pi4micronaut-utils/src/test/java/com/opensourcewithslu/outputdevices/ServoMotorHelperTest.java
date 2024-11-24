package com.opensourcewithslu.outputdevices;

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
        servoMotorHelper.enable();
        verify(servoMotor).on(0, 50);
        verify(log).info("Enabling servo motor");
        assertTrue(servoMotorHelper.isEnabled());
    }

    @Test
    void disables() {
        servoMotorHelper.enable();
        servoMotorHelper.disable();
        verify(servoMotor).off();
        verify(log).info("Disabling servo motor");
        assertFalse(servoMotorHelper.isEnabled());
    }

    @Test
    void setAngleFailsWhenDisabled() {
        servoMotorHelper.setAngle(90);
        verify(log).info("You must enable the servo motor first.");
        verify(servoMotor, never()).on(0, 50);
        verify(log, never()).info("Setting angle to {} degrees", 90);
    }

    @Test
    void setAngleLowFails() {
        servoMotorHelper.enable();
        servoMotorHelper.setAngle(-10);
        verify(log).info("You must enter an angle between 0 and 180 degrees.");

        float pulseWidth = servoMotorHelper.map(-10);
        float dutyCycle = (pulseWidth / PWM_CYCLE_MICROSECONDS) * 100;

        verify(servoMotor, never()).on(dutyCycle, FREQUENCY);
        verify(log, never()).info("Setting angle to {} degrees", -10);
    }

    @Test
    void setAngleHighFails() {
        servoMotorHelper.enable();
        servoMotorHelper.setAngle(181);
        verify(log).info("You must enter an angle between 0 and 180 degrees.");

        float pulseWidth = servoMotorHelper.map(181);
        float dutyCycle = (pulseWidth / PWM_CYCLE_MICROSECONDS) * 100;

        verify(servoMotor, never()).on(dutyCycle, FREQUENCY);
        verify(log, never()).info("Setting angle to {} degrees", 181);
    }

    @Test
    void setAngleAtMinimumValue() {
        servoMotorHelper.enable();
        servoMotorHelper.setAngle(0);

        float pulseWidth = servoMotorHelper.map(0);
        float dutyCycle = (pulseWidth / PWM_CYCLE_MICROSECONDS) * 100;

        verify(log).info("Setting servo to {} degrees, Pulse Width: {} us, Duty Cycle: {}%", 0, pulseWidth, dutyCycle);
        verify(servoMotor).on(dutyCycle, FREQUENCY);
    }

    @Test
    void setAngleAtMaximumValue() {
        servoMotorHelper.enable();

        float pulseWidth = servoMotorHelper.map(180);
        float dutyCycle = (pulseWidth / PWM_CYCLE_MICROSECONDS) * 100;

        servoMotorHelper.setAngle(180);
        verify(log).info("Setting servo to {} degrees, Pulse Width: {} us, Duty Cycle: {}%", 180, pulseWidth, dutyCycle);
        verify(servoMotor).on(dutyCycle, FREQUENCY);
    }

    @Test
    void setAngle() {
        servoMotorHelper.enable();

        float pulseWidth = servoMotorHelper.map(90);
        float dutyCycle = (pulseWidth / PWM_CYCLE_MICROSECONDS) * 100;

        servoMotorHelper.setAngle(90);
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
