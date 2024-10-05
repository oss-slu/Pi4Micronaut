package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.pwm.Pwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class to control a DC motor using PWM (Pulse Width Modulation).
 * This class provides methods to enable, disable, and set the speed of a DC motor.
 */
public class MotorHelper {

    private static final Logger log = LoggerFactory.getLogger(MotorHelper.class);
    private static final int FREQUENCY = 50; // Frequency for PWM signal in Hz, typical for DC motors.
    private boolean isEnabled = false; // State tracking variable for the DC motor.
    private final Pwm DCMotor; // PWM interface for the DC motor.
    private final DigitalOutput pin1; // GPIO pin 1 for motor direction.
    private final DigitalOutput pin2; // GPIO pin 2 for motor direction.
    private boolean isClockwise = true; // Direction of the DC motor.

    /**
     * Constructs a new DCMotorHelper.
     *
     * @param DCMotor A PWM interface to control the DC motor.
     */
    public MotorHelper(Pwm DCMotor, DigitalOutput pin1, DigitalOutput pin2) {
        this.DCMotor = DCMotor;
        this.pin1 = pin1;
        this.pin2 = pin2;
    }

    /**
     * Enables the DC motor by setting an initial duty cycle and frequency.
     * The DC motor remains disabled until this method is called.
     */
    public void enable() {
        log.info("Enabling DC motor");
        DCMotor.on(0, FREQUENCY); // Initializes PWM signal with 0% duty cycle.
        isEnabled = true;
    }

    /**
     * Disables the DC motor, effectively stopping any ongoing PWM signal.
     */
    public void disable() {
        log.info("Disabling DC motor");
        DCMotor.off(); // Stops the PWM signal.
        isEnabled = false;
    }

    /**
     * Sets the speed of the DC motor.
     * This method calculates the necessary pulse width and duty cycle to achieve the specified speed.
     *
     * @param speed the target speed for the DC motor, as a percentage between 0 and 1.
     */
    public void setSpeed(double speed) {
        if (!isEnabled) {
            log.info("You must enable the DC motor first.");
            return;
        }

        if (speed < 0 || speed > 1) {
            log.info("You must enter a speed between 0 and 1.");
            return;
        }

        log.info("Setting DC speed to {}%", speed);

        DCMotor.on(speed, FREQUENCY);
    }

    /**
     * Sets the direction of the DC motor.
     *
     * @param clockwise whether the DC motor should rotate clockwise.
     */
    public void setClockwise(boolean clockwise) {
        if (!isEnabled) {
            log.info("You must enable the DC motor first.");
            return;
        }

        log.info("Setting DC motor direction clockwise to {}", clockwise);
        if (clockwise) {
            pin1.high();
            pin2.low();
        } else {
            pin1.low();
            pin2.high();
        }

        isClockwise = clockwise;
    }

    /**
     * Switches the direction of the DC motor.
     */
    public void switchDirection() {
        setClockwise(!isClockwise);
    }
}
