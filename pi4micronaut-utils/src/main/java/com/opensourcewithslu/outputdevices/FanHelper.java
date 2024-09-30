package com.myapp;

import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmConfig;
import com.pi4j.io.pwm.PwmType;
import com.pi4j.plugin.pigpio.provider.pwm.PiGpioPwmProviderImpl;
import com.pi4j.library.pigpio.PiGpio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FANHelper {

    private static final Logger log = LoggerFactory.getLogger(FANHelper.class);
    private final Pwm pwm;

    // Constructor that takes Pwm
    public FANHelper(Pwm pwm) {
        this.pwm = pwm;
    }

    public void start() {
        log.info("Starting Fan at full speed");
        pwm.on(1024);  // 1024 is full speed (100% duty cycle)
    }

    public void stop() {
        log.info("Stopping Fan");
        pwm.off();  // Turn off the fan (0% duty cycle)
    }

    public void setSpeed(int speed) {
        if (speed < 0 || speed > 1024) {
            throw new IllegalArgumentException("Speed must be between 0 and 1024");
        }
        log.info("Setting fan speed to " + speed);
        pwm.on(speed);  // Adjust the duty cycle based on the speed
    }
}
