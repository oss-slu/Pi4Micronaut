package com.opensourcewithslu.outputdevices;

import com.pi4j.io.pwm.Pwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Helper class to control a fan using PWM (Pulse Width Modulation).
 */
public class FanHelper {

    private static final Logger log = LoggerFactory.getLogger(FanHelper.class);
    private final Pwm pwm;

    /**
     * Constructor to initialize the FanHelper with a PWM instance.
     *
     * @param fan the PWM instance to control the fan
     */
    public FanHelper(Pwm fan) {
        this.pwm = fan;
    }

    /**
     * Starts the fan at full speed (1024 duty cycle) for 10 seconds, then stops it.
     */
    public void start() {
        log.info("Starting Fan at full speed");
        try {
            pwm.on(1024);  // Full speed
            TimeUnit.SECONDS.sleep(10);  // Let the fan run for 10 seconds
            pwm.off();  // Turn off the fan
        } catch (InterruptedException e) {
            log.error("Test interrupted", e);
        }
    }

    /**
     * Stops the fan by turning off the PWM (0% duty cycle).
     */
    public void stop() {
        log.info("Stopping Fan");
        pwm.off();  // Turn off the fan (0% duty cycle)
    }

    /**
     * Sets the fan speed by adjusting the PWM duty cycle.
     *
     * @param speed the desired fan speed (duty cycle). Must be between 0 and 1024.
     * @throws IllegalArgumentException if the speed is out of bounds
     */
    public void setSpeed(int speed) {
        if (speed < 0 || speed > 1024) {
            throw new IllegalArgumentException("Speed must be between 0 and 1024");
        }
        log.info("Setting fan speed to {}", speed);
        pwm.on(speed);  // Adjust the duty cycle based on the speed
    }
}
