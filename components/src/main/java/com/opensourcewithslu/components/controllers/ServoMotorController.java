package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.PushButtonHelper;
import com.opensourcewithslu.outputdevices.LEDHelper;
import com.opensourcewithslu.outputdevices.ServoMotorHelper;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import javax.validation.constraints.Positive;

@Controller("/servo")
public class ServoMotorController {
    private final ServoMotorHelper servoMotorHelper;

    public ServoMotorController(ServoMotorHelper servoMotorHelper) {
        this.servoMotorHelper = servoMotorHelper;
    }

    @Get("/init")
    public void initController(){

    }

    @Post("/{i}")
    public void update(@Positive int i) {
        servoMotorHelper.setAngle(i);
    }
}
