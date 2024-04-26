package com.opensourcewithslu.outputdevices;

import com.pi4j.io.pwm.Pwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class to control a servo motor using PWM (Pulse Width Modulation).
 * This class provides methods to enable, disable, and set the angle of a servo motor.
 */
public class ServoMotorHelper {

    private static final Logger log = LoggerFactory.getLogger(ServoMotorHelper.class);
    private static final int FREQUENCY = 50; // Frequency for PWM signal in Hz, typical for servo motors.
    private static final int PWM_CYCLE_MICROSECONDS = 20000; // Total cycle length in microseconds, corresponding to 50 Hz.
    private static final int MIN_ANGLE = 0; // Minimum angle for servo operation.
    private static final int MAX_ANGLE = 180; // Maximum angle for servo operation.
    private static final int SERVO_MIN_PULSE = 500; // Minimum pulse width in microseconds corresponding to 0 degrees.
    private static final int SERVO_MAX_PULSE = 2500; // Maximum pulse width in microseconds corresponding to 180 degrees.
    private boolean isEnabled = false; // State tracking variable for the servo motor.
    private final Pwm servoMotor; // PWM interface for the servo motor.

    /**
     * Constructs a new ServoMotorHelper.
     *
     * @param servoMotor A PWM interface to control the servo motor.
     */
    public ServoMotorHelper(Pwm servoMotor) {
        this.servoMotor = servoMotor;
    }

    /**
     * Enables the servo motor by setting an initial duty cycle and frequency.
     * The servo motor remains disabled until this method is called.
     */
    public void enable() {
        log.info("Enabling servo motor");
        servoMotor.on(0, FREQUENCY); // Initializes PWM signal with 0% duty cycle.
        isEnabled = true;
    }

    /**
     * Disables the servo motor, effectively stopping any ongoing PWM signal.
     */
    public void disable() {
        log.info("Disabling servo motor");
        servoMotor.off(); // Stops the PWM signal.
        isEnabled = false;
    }

    /**
     * Maps the given angle to the corresponding pulse width for the servo motor.
     *
     * @param value the angle in degrees to be mapped to pulse width.
     * @return the calculated pulse width in microseconds.
     */
    private float map(float value) {
        return SERVO_MIN_PULSE + ((value - MIN_ANGLE) * (SERVO_MAX_PULSE - SERVO_MIN_PULSE) / (MAX_ANGLE - MIN_ANGLE));
    }

    /**
     * Sets the servo motor to a specific angle.
     * This method calculates the necessary pulse width and duty cycle to achieve the specified angle.
     *
     * @param angle the target angle for the servo motor, between 0 and 180 degrees.
     */
    public void setAngle(int angle) {
        if (!isEnabled) {
            log.info("You must enable the servo motor first.");
            return;
        }

        if (angle < MIN_ANGLE || angle > MAX_ANGLE) {
            log.info("You must enter an angle between 0 and 180 degrees.");
            return;
        }

        float pulseWidth = map(angle);
        float dutyCycle = (pulseWidth / PWM_CYCLE_MICROSECONDS) * 100;

        log.info("Setting servo to {} degrees, Pulse Width: {} us, Duty Cycle: {}%", angle, pulseWidth, dutyCycle);

        servoMotor.on(dutyCycle, FREQUENCY);
        try {
            Thread.sleep(100); // Pauses the thread to allow the servo to reach the set angle.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Properly handle thread interruption.
            log.info("Thread was interrupted, failed to complete rotation");
        }
    }
}
