package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.LEDHelper;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/led")
public class LEDController {
    private final LEDHelper ledHelper;

    public LEDController(LEDHelper led){
        this.ledHelper = led;
    }

    @Get("/ledOn")
    public void ledOn(){
        /*deviceOff method turns on the LED after checking the output. This
        will turn on the LED
        */

        ledHelper.deviceOff();
    }

    @Get("/ledOff")
    public void ledOff(){

        ledHelper.deviceOn();
    }

    @Get("/switchState")
    public void switchState(){

        ledHelper.switchState();
    }
}
