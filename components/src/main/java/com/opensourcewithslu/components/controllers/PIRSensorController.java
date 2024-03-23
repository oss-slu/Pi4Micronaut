package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.PIRSensorHelper;
import com.opensourcewithslu.outputdevices.RGBLEDHelper;
import com.opensourcewithslu.utilities.MultiPinConfiguration;
import com.pi4j.io.gpio.digital.DigitalInput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

/**
 * The PIRSensorController class is used with the PIRSensorHelper class and RGBHelper class to implement a PIR motion sensor with an RGB LED light.
 */
@Controller("/pirSensor")
public class PIRSensorController {

    private final PIRSensorHelper pirSensorHelper;

    private final RGBLEDHelper rgbledHelper;

    /**
     * The PirSensorController constructor.
     * @param pirSensor A Pi4J DigitalInput object.
     * @param rgbLed A MultiPinConfiguration object.
     */
    public PIRSensorController(@Named("pir-sensor")DigitalInput pirSensor,
                               @Named("rgb-led-2") MultiPinConfiguration rgbLed) {
        this.pirSensorHelper = new PIRSensorHelper(pirSensor);
        this.rgbledHelper = new RGBLEDHelper(rgbLed);
    }

    /**
     * Enables the PIR sensor by adding an event listener which sets the RGB LED to red when movement is detected and green otherwise.
     */
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

    /**
     * Disables the controller by removing the event listener and turning off the RGB LED.
     */
    @Get("/disable")
    public void disablePIRSensor() {
        pirSensorHelper.removeEventListener();
        rgbledHelper.ledOff();
    }

}