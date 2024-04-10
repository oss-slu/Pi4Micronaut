package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.ServoMotorHelper;
import com.pi4j.io.pwm.Pwm;
import io.micronaut.http.annotation.Controller;
import jakarta.inject.Named;

@Controller("/servoMotor")
public class ServoMotorController {

    private final ServoMotorHelper servoMotorHelper;

    public ServoMotorController(@Named("servo-motor")Pwm servoMotor) {
        this.servoMotorHelper = new ServoMotorHelper(servoMotor);
    }
}
