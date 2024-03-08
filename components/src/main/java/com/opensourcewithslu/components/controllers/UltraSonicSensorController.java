package com.opensourcewithslu.components.controllers;


import com.opensourcewithslu.inputdevices.UltraSonicSensorHelper;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;


@Controller("/ultraSound")
public class UltraSonicSensorController {

    private final UltraSonicSensorHelper ultraSonicSensorHelper;

    public UltraSonicSensorController(@Named("ultra-sonic-trig") DigitalOutput trig,
                                      @Named("ultra-sonic-echo") DigitalInput echo) {
        this.ultraSonicSensorHelper = new UltraSonicSensorHelper(trig,echo);

    }


    @Get("/enable")
    public String enableUltraSonicSensor() {
        this.ultraSonicSensorHelper.startMeasuring();
        return "Ultra Sonic Sensor Enabled \nIf the distance is constantly Zero, make sure the sensor field of view is clear \n";
    }

    @Get("/distance/cm")
    public String getDistanceInCentimeter() {
        return this.ultraSonicSensorHelper.getDistanceInCentimeter() + " cm\n";
    }

    @Get("/distance/m")
    public String getDistanceInMeter() {
        return this.ultraSonicSensorHelper.getDistanceInMeters() + " m\n";
    }

    @Get("/disable")
    public String disableUltrasoundSensor() {
        this.ultraSonicSensorHelper.stopMeasuring();
        return "Ultra Sonic Sensor Disabled";
    }
}
