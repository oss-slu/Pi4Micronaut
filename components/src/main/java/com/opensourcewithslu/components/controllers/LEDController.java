package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.LEDHelper;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;


@Controller("/led")
public class LEDController {
    private final LEDHelper ledHelper;
    private final LEDHelper ledHelper2;

    public LEDController(@Named("Led")DigitalOutput led, @Named("Led2")DigitalOutput led2){
        this.ledHelper = new LEDHelper(led);
        this.ledHelper2 = new LEDHelper(led2);
    }

    @Get("/led1")
    public void checkLed1(){
        /*deviceOff method turns on the LED after checking the output. This
        will turn on the LED
        */

        ledHelper.deviceOff();
    }
    @Get("/led2")
    public void checkLed2(){
        ledHelper2.deviceOff();
    }

}
