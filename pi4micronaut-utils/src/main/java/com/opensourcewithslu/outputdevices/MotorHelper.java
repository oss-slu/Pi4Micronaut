package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.pwm.Pwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class to control a DC motor using PWM (Pulse Width Modulation).
 * This class provides methods to enable, disable, and set the speed of a motor.
 */
public class MotorHelper {

    private static Logger log = LoggerFactory.getLogger(MotorHelper.class);
    private static final int FREQUENCY = 50; // Frequency for PWM signal in Hz.
    private boolean isEnabled = false; // State tracking variable for the motor.
    private final Pwm motor; // PWM interface for the motor.
    private final DigitalOutput pin1; // GPIO pin 1 for motor direction.
    private final DigitalOutput pin2; // GPIO pin 2 for motor direction.
    private boolean isClockwise = true; // Direction of the motor.

    /**
     * Constructs a new MotorHelper.
     *
     * @param motor A PWM interface to control the motor.
     * @param pin1 A DigitalOutput interface for the first GPIO pin.
     * @param pin2 A DigitalOutput interface for the second GPIO pin.
     */
    //tag::const[]
    public MotorHelper(Pwm motor, DigitalOutput pin1, DigitalOutput pin2)
    //end::const[]
    {
        this.motor = motor;
        this.pin1 = pin1;
        this.pin2 = pin2;
    }

    /**
     * Enables the DC motor by setting an initial duty cycle and frequency.
     * The motor remains disabled until this method is called.
     */
    //tag::[method]
    public void enable()
    //end::[method]
    {
        log.info("Enabling DC motor");
        motor.on(0, FREQUENCY); // Initializes PWM signal with 0% duty cycle.
        isEnabled = true;
    }

    /**
     * Disables the motor, effectively stopping any ongoing PWM signal.
     */
    //tag::[method]
    public void disable()
    //end::[method]
    {
        log.info("Disabling DC motor");
        motor.off(); // Stops the PWM signal.
        isEnabled = false;
    }

    /**
     * Sets the speed of the DC motor.
     * This method calculates the necessary pulse width and duty cycle to achieve the specified speed.
     *
     * @param speed the target speed for the motor, as a percentage between 0 and 1.
     */
    //tag::[method]
    public void setSpeed(double speed)
    //end::[method]
    {
        if (!isEnabled) {
            log.info("You must enable the DC motor first.");
            return;
        }

        if (speed < 0 || speed > 100) {
            log.info("You must enter a speed between 0 and 100.");
            return;
        }

        log.info("Setting motor speed to {}%", speed);

        motor.on(speed, FREQUENCY);
    }

    /**
     * Sets the direction of the DC motor.
     *
     * @param clockwise whether the motor should rotate clockwise.
     */
    //tag::[method]
    public void setClockwise(boolean clockwise)
    //end::[method]
    {
        if (!isEnabled) {
            log.info("You must enable the DC motor first.");
            return;
        }

        log.info("Setting motor direction clockwise to {}", clockwise);
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
     * Switches the direction of the motor.
     */
    //tag::[method]
    public void switchDirection()
    //end::[method]
    {
        setClockwise(!isClockwise);
    }

    /**
     * Sets the logger object.
     * This method is intended for internal testing purposes only.
     *
     * @param log Logger object to set the logger to.
     */
    void setLog(Logger log) {
        this.log = log;
    }
}
