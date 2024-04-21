package com.opensourcewithslu.outputdevices;

import com.pi4j.io.pwm.Pwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ServoMotorHelper class contains methods which are used to implement and control a servo motor.
 */
public class ServoMotorHelper {

    private static final Logger log = LoggerFactory.getLogger(ServoMotorHelper.class);
    private static final int FREQUENCY = 50; // Frequency for PWM signal in Hz
    private static final int PWM_CYCLE_MICROSECONDS = 2000; // Total cycle length in microseconds
    private static final int MIN_ANGLE = 0;
    private static final int MAX_ANGLE = 180;
    private static final int SERVO_MIN_PULSE = 500; // Minimum pulse width in microseconds (0.5ms for 0 degrees)
    private static final int SERVO_MAX_PULSE = 2500; // Maximum pulse width in microseconds (2.5ms for 180 degrees)
    private boolean isEnabled = false; // State tracking variable for the servo motor
    private final Pwm servoMotor;

    /**
     * The ServoMotorHelper constructor.
     * @param servoMotor A {@link  com.opensourcewithslu.utilities.PwmConfiguration} Object.
     */
    public ServoMotorHelper(Pwm servoMotor)
    {
        this.servoMotor = servoMotor;
    }

    /**
     * Enables the servo motor by setting the duty cycle to 0 and the frequency to 50.
     */
    public void enable()
    {
        log.info("Enabling servo motor");
        servoMotor.on(0, FREQUENCY);
        isEnabled = true;
    }

    /**
     * Disables the servo motor.
     */
    public void disable()
    {
        log.info("Disabling servo motor");
        servoMotor.off();
        isEnabled = false;
    }

    private float map(float value, int inMax, int outMin, int outMax)
    {
        return (outMax - outMin) * (value) / (inMax) + outMin;
    }

    /**
     * Takes the angle as input and rotates the servo motor by that amount (between 0 and 180 degrees).
     * @param angle An integer type
     */
    public void setAngle(int angle) {
        if (!isEnabled) {
            log.info("You must enable the servo motor first.");
            return;
        }

        angle = Math.max(MIN_ANGLE, Math.min(MAX_ANGLE, angle));

        float pulseWidth = map(angle, MAX_ANGLE, SERVO_MIN_PULSE, SERVO_MAX_PULSE);
        float dutyCycle = map(pulseWidth, PWM_CYCLE_MICROSECONDS, 0, 100);

        log.info("Setting servo to {} degrees, Pulse Width: {} us, Duty Cycle: {}%", angle, pulseWidth, dutyCycle);

        servoMotor.on(dutyCycle);
    }
}
