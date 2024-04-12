package com.opensourcewithslu.outputdevices;

import com.pi4j.io.pwm.Pwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class ServoMotorHelper {

    private static final Logger log = LoggerFactory.getLogger(ServoMotorHelper.class);

    private final Pwm servoMotor;

    /**
     *
     * @param servoMotor PWM
     */
    public ServoMotorHelper(Pwm servoMotor)
    {
        this.servoMotor = servoMotor;
    }

    /**
     *
     */
    public void enable()
    {
        log.info("enabling servo motor");

        servoMotor.on(0, 50);
    }

    /**
     *
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
     *
     * @param angle integer
     */
    public void setAngle(int angle)
    {
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
