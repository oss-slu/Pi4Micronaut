package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.PIRSensorHelper;
import com.opensourcewithslu.outputdevices.RGBLEDHelper;
import com.opensourcewithslu.utilities.MultipinConfiguration;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

@Controller("/pirSensor")
public class PIRSensorController {

    private final PIRSensorHelper pirSensorHelper;

    private final RGBLEDHelper rgbledHelper;

    public PIRSensorController(@Named("pir-sensor-output")DigitalOutput pirSensor,
                               @Named("rgb-led-2")MultipinConfiguration rgbled) {
        this.pirSensorHelper = new PIRSensorHelper(pirSensor);
        this.rgbledHelper = new RGBLEDHelper(rgbled);
    }

    @Get("/enable")
    public void enablePIRSensor() {

        int[] yellow = {255,255,0};
        int[] blue = {0,0,255};

        pirSensorHelper.addEventListener(e -> {
            if (pirSensorHelper.isMoving) {
                rgbledHelper.setColor(yellow);
            }
            else {
                rgbledHelper.setColor(blue);
            }
        });
    }

    @Get("/disable")
    public void disablePIRSensor() {
        pirSensorHelper.removeEventListener();
        rgbledHelper.ledOff();
    }

}