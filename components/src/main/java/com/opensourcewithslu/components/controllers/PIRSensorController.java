package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.PIRSensorHelper;
import com.opensourcewithslu.outputdevices.RGBLEDHelper;
import com.opensourcewithslu.utilities.MultipinConfiguration;
import com.pi4j.io.gpio.digital.DigitalInput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

@Controller("/pirSensor")
public class PIRSensorController {

    private final PIRSensorHelper pirSensorHelper;

    private final RGBLEDHelper rgbledHelper;

    public PIRSensorController(@Named("pir-sensor-input")DigitalInput pirSensor,
                               @Named("rgb-led-2")MultipinConfiguration rgbLed) {
        this.pirSensorHelper = new PIRSensorHelper(pirSensor);
        this.rgbledHelper = new RGBLEDHelper(rgbLed);
    }

    @Get("/enable")
    public void enablePIRSensor() {

        int[] red = {255,0,0};
        int[] green = {0,255,0};

        pirSensorHelper.addEventListener(e -> {
            if (pirSensorHelper.isMoving) {
                rgbledHelper.setColor(red);
            }
            else {
                rgbledHelper.setColor(green);
            }
        });
    }

    @Get("/disable")
    public void disablePIRSensor() {
        pirSensorHelper.removeEventListener();
        rgbledHelper.ledOff();
    }

}