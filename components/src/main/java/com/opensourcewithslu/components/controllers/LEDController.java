package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.LEDHelper;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

//tag::ex[]
@Controller("/led")
public class LEDController {
    private final LEDHelper ledHelper;

    public LEDController(@Named("led")DigitalOutput led){
        this.ledHelper = new LEDHelper(led);
    }

    @Get("/ledOn")
    public void ledOn(){
        ledHelper.ledOn();
    }

    @Get("/ledOff")
    public void ledOff(){
        ledHelper.ledOff();
    }

    @Get("/switchState")
    public void switchState(){
        ledHelper.switchState();
    }

    @Get("/blink/{duration}/")
    public void blink(int duration){
        ledHelper.blink(duration);
    }
}
//end::ex[]