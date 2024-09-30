package com.myapp;

import com.pi4j.io.pwm.Pwm;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;

@Controller("/fan")
public class FanController {

    private final FANHelper fanHelper;

    @Inject
    public FanController(@Value("${pi4j.pwm.fan.address}") int fanPin, Pwm pwm) {
        this.fanHelper = new FANHelper(pwm);
    }

    @Get("/turnon")
    public String startFan() {
        fanHelper.start();
        return "Fan started at full speed";
    }

    @Get("/turndown")
    public String stopFan() {
        fanHelper.stop();
        return "Fan stopped";
    }

    @Get("/setspeed/{speed}")
    public String setFanSpeed(int speed) {
        fanHelper.setSpeed(speed);
        return "Fan speed set to " + speed;
    }
}
