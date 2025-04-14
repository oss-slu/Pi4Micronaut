package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.PIRSensorHelper;
import com.opensourcewithslu.outputdevices.RGBLEDHelper;
import com.opensourcewithslu.utilities.MultiPinConfiguration;
import com.pi4j.io.gpio.digital.DigitalInput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The PIRSensorController class is used with the PIRSensorHelper class and RGBHelper class to implement a PIR motion sensor with an RGB LED light.
 */
//tag::ex[]
@Controller("/pirSensor")
public class PIRSensorController {
    private static final Logger log = LoggerFactory.getLogger(PIRSensorController.class);
    private final PIRSensorHelper pirSensorHelper;
    private final RGBLEDHelper rgbledHelper;

    /**
     * The PirSensorController constructor.
     * @param pirSensor A Pi4J DigitalInput object.
     * @param rgbLed A MultiPinConfiguration object.
     */
    public PIRSensorController(@Named("pir-sensor") DigitalInput pirSensor, @Named("rgb-led-2") MultiPinConfiguration rgbLed) {
        this.pirSensorHelper = new PIRSensorHelper(pirSensor);
        this.rgbledHelper = new RGBLEDHelper(rgbLed);
    }
    /**
     * Enables the PIR sensor by adding an event listener which sets the RGB LED to red when movement is detected and green otherwise.
     */
    @Get("/enable")
    public void enablePIRSensor() {
        log.info("Enabling PIR sensor and setting up event listener");
        int[] red = {255,0,0};
        int[] green = {0,255,0};
        pirSensorHelper.addEventListener(e -> {
            if (pirSensorHelper.isMoving) {
                log.info("Motion Detected - LED Red");
                rgbledHelper.setColor(red);
            }
            else {
                log.info("No Motion Detected - LED Green");
                rgbledHelper.setColor(green);
            }
        });
    }
    /**
     * Disables the controller by removing the event listener and turning off the RGB LED.
     */
    @Get("/disable")
    public void disablePIRSensor() {
        log.info("Disabling PIR sensor and removing event listener");
        pirSensorHelper.removeEventListener();
        rgbledHelper.ledOff();
    }
}
//end::ex[]