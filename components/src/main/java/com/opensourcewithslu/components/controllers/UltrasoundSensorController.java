package com.opensourcewithslu.components.controllers;


import com.opensourcewithslu.inputdevices.UltrasoundSensorHelper;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;


@Controller("/ultraSound")
public class UltrasoundSensorController {

    private final UltrasoundSensorHelper ultrasoundSensorHelper;

    public UltrasoundSensorController(@Named("ultrasound-input") DigitalInput ultraSoundIN,
                                      @Named("ultrasound-output") DigitalOutput ultraSoundOUT) {
        this.ultrasoundSensorHelper = new UltrasoundSensorHelper(ultraSoundIN, ultraSoundOUT);

    }


    @Get("/enable")
    public void enableUltrasoundSensor() {
        this.ultrasoundSensorHelper.initialize();
    }
}
