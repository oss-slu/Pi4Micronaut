package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.DCMotorHelper;
import com.pi4j.io.pwm.Pwm;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

//tag::ex[]
@Controller("/DCMotor")
public class DCMotorController {
    private final DCMotorHelper DCMotorHelper;

    public DCMotorController(@Named("DC-motor") Pwm DCMotor) {
        this.DCMotorHelper = new DCMotorHelper(DCMotor);
    }

    @Get("/enable")
    public void enableDCMotor() {
        DCMotorHelper.enable();
    }

    @Get("/disable")
    public void disableDCMotor() {
        DCMotorHelper.disable();
    }

    @Get("/setAngle/{angle}")
    public void setAngle(int angle) {
        DCMotorHelper.setAngle(angle);
    }
}
//end::ex[]
