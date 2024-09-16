package com.opensourcewithslu.components.controllers;

import org.slf4j.LoggerFactory;

import com.opensourcewithslu.outputdevices.LEDHelper;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//tag::ex[]
@Controller("/led")
public class LEDController {
    private static final Logger log = LoggerFactory.getLogger(LEDController.class);

    private final LEDHelper ledHelper;

    public LEDController(@Named("led")DigitalOutput led){
        this.ledHelper = new LEDHelper(led);
    }

    @Get("/ledOn")
    public void ledOn(){
        try {
            ledHelper.ledOn();
        } catch (Exception e) {
            log.error("Error turning on LED", e);
        }
    }

    @Get("/ledOff")
    public void ledOff(){
        try {
            ledHelper.ledOff();
        } catch (Exception e) {
            log.error("Error turning off LED", e);
        }
    }

    @Get("/switchState")
    public void switchState(){
        try {
            ledHelper.switchState();
        } catch (Exception e) {
            log.error("Error switching LED state", e);
        }
    }

    @Get("/blink/{duration}/")
    public void blink(int duration){
        try {
            ledHelper.blink(duration);
        } catch (Exception e) {
            log.error("Error blinking LED", e);
        }
    }
}
//end::ex[]