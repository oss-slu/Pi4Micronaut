package com.opensourcewithslu.outputdevices;

import com.pi4j.io.pwm.Pwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ServoMotorHelper class contains methods which are used to implement and control a servo motor.
 */
public class ServoMotorHelper {

    private static final Logger log = LoggerFactory.getLogger(ServoMotorHelper.class);

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
        log.info("enabling servo motor");

        servoMotor.on(0, 50);
    }

    /**
     * Disables the servo motor.
     */
    public void disable()
    {
        log.info("disabling servo motor");

        servoMotor.off();
    }

    private float map(float value, int inMax, int outMin, int outMax)
    {
        return (outMax - outMin) * (value) / (inMax) + outMin;
    }

    /**
     * Takes the angle as input and rotates the servo motor by that amount (between 0 and 180 degrees).
     * @param angle An integer type
     */
    public void setAngle(int angle)
    {
        log.info("Rotating the servo motor by " + angle + " degrees.");

        int MIN_ANGLE = 0;
        int MAX_ANGLE = 180;
        int SERVO_MIN_PULSE = 500;
        int SERVO_MAX_PULSE = 2500;

        angle = Math.max(MIN_ANGLE, Math.min(MAX_ANGLE, angle));
        float pulse = map(angle, MAX_ANGLE, SERVO_MIN_PULSE, SERVO_MAX_PULSE);
        float pwm = map(pulse, 20000, 0, 100);

        servoMotor.setDutyCycle(pwm);
    }
}
