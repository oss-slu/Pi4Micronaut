package com.opensourcewithslu.components.controllers;


import com.opensourcewithslu.inputdevices.LightSensorHelper;
import com.opensourcewithslu.inputdevices.Darkness;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Positive;

@Controller("/light")
public class LightSensorController {

    private final LightSensorHelper lightSensorHelper;
    private static final Logger log = LoggerFactory.getLogger(LightSensorController.class);

    public LightSensorController(LightSensorHelper lightSensorHelper) {
        this.lightSensorHelper = lightSensorHelper;
    }

    @Get
    public Darkness status() {
        return this.lightSensorHelper.getDarkness();
    }

    @Post("/threshold/{i}")
    public HttpResponse update(@Positive int i) {
        this.lightSensorHelper.setDarknessThreshold(i);
        return HttpResponse.noContent();
    }

}
