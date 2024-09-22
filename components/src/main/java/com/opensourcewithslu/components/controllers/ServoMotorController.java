package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.ServoMotorHelper;
import com.pi4j.io.pwm.Pwm;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

//tag::ex[]
@Controller("/servoMotor")
public class ServoMotorController {
    private final ServoMotorHelper servoMotorHelper;

    public ServoMotorController(@Named("servo-motor")Pwm servoMotor) {
        this.servoMotorHelper = new ServoMotorHelper(servoMotor);
    }

    @Get("/enable")
    public void enableServoMotor() {
        servoMotorHelper.enable();
    }

    @Get("/disable")
    public void disableServoMotor() {
        servoMotorHelper.disable();
    }

    @Get("/setAngle/{angle}")
    public void setAngle(int angle) {
        servoMotorHelper.setAngle(angle);
    }
}
//end::ex[]
