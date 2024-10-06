package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.FanHelper;
import com.pi4j.io.pwm.Pwm;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import jakarta.inject.Named;

@Controller("/fan")
public class FanController {
    private final FanHelper fanHelper;

    public FanController(@Named("fan") Pwm fa) {
        this.fanHelper = new FanHelper(fa);
    }

    @Get("/start")
    public void start() {
        fanHelper.start();
    }

    @Get("/stop")
    public void stop() {
        fanHelper.stop();
    }

    @Get("/setSpeed/{speed}")
    public void setSpeed(@PathVariable int speed) {
        fanHelper.setSpeed(speed);
    }
}