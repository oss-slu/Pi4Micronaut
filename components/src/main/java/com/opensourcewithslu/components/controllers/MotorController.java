package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.MotorHelper;
import com.pi4j.io.pwm.Pwm;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

//tag::ex[]
@Controller("/DCMotor")
public class MotorController {
    private final MotorHelper MotorHelper;

    public MotorController(@Named("DC-motor") Pwm DCMotor) {
        this.MotorHelper = new MotorHelper(DCMotor);
    }

    @Get("/enable")
    public void enableDCMotor() {
        MotorHelper.enable();
    }

    @Get("/disable")
    public void disableDCMotor() {
        MotorHelper.disable();
    }

    @Get("/setSpeed/{speed}")
    public void setSpeed(double speed) {
        MotorHelper.setSpeed(speed);
    }

    @Get("/setClockwise/{clockwise}")
    public void setClockwise(boolean clockwise) {
        MotorHelper.setClockwise(clockwise);
    }

    @Get("/switchDirection")
    public void switchDirection() {
        MotorHelper.switchDirection();
    }
}
//end::ex[]
