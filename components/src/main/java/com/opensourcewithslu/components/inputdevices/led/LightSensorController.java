package com.opensourcewithslu.components.inputdevices.led;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import javax.validation.constraints.Positive;

@Controller("/light")
public class LightSensorController {

    private final LightSensorHelper sensorHelper;

    public LightSensorController(LightSensorHelper sensorHelper) {
        this.sensorHelper = sensorHelper;
    }

    @Get
    public Darkness status() {
        return sensorHelper.getDarkness();
    }

    @Post("/threshold/{i}")
    public HttpResponse update(@Positive int i) {
        sensorHelper.setDarknessThreshold(i);
        return HttpResponse.noContent();
    }
}
