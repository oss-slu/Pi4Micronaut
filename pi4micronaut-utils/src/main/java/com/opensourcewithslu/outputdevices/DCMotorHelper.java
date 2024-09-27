package com.opensourcewithslu.outputdevices;

import com.pi4j.io.pwm.Pwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class to control a DC motor using PWM (Pulse Width Modulation).
 * This class provides methods to enable, disable, and set the speed of a DC motor.
 */
public class DCMotorHelper {

    private static final Logger log = LoggerFactory.getLogger(DCMotorHelper.class);
    private static final int FREQUENCY = 50; // Frequency for PWM signal in Hz, typical for DC motors.
    private static final int PWM_CYCLE_MICROSECONDS = 20000; // Total cycle length in microseconds, corresponding to 50 Hz.
    private static final int DC_MIN_PULSE = 500; // Minimum pulse width in microseconds corresponding to 0 degrees.
    private static final int DC_MAX_PULSE = 2500; // Maximum pulse width in microseconds corresponding to 180 degrees.
    private boolean isEnabled = false; // State tracking variable for the DC motor.
    private double speed = 0; // Speed of the DC motor, as a percentage 0 to 1.
    private final Pwm DCMotor; // PWM interface for the DC motor.

    /**
     * Constructs a new DCMotorHelper.
     *
     * @param DCMotor A PWM interface to control the DC motor.
     */
    public DCMotorHelper(Pwm DCMotor) {
        this.DCMotor = DCMotor;
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
     * Maps the given speed to the corresponding pulse width for the DC motor.
     *
     * @param value the speed in degrees to be mapped to pulse width.
     * @return the calculated pulse width in microseconds.
     */
    private float map(float value) {
        return DC_MIN_PULSE + (value * (DC_MAX_PULSE - DC_MIN_PULSE));
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

        this.speed = speed;
        float pulseWidth = map((float) (speed * 180));
        float dutyCycle = (pulseWidth / PWM_CYCLE_MICROSECONDS) * 100;

        log.info("Setting DC speed to {}%, Pulse Width: {} us, Duty Cycle: {}%", speed * 100, pulseWidth, dutyCycle);

        DCMotor.on(dutyCycle, FREQUENCY);
    }
}
