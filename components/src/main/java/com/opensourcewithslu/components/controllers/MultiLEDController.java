package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.PushButtonHelper;
import com.opensourcewithslu.outputdevices.LEDHelper;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Named;

@Controller("/led")
public class MultiLEDController {
    private LEDHelper ledHelper;
    private LEDHelper ledHelper2;

    public MultiLEDController(@Named("led") DigitalOutput led1, @Named("led2") DigitalOutput led2) {
        this.ledHelper = new LEDHelper(led1);

        this.ledHelper2 = new LEDHelper(led2);
    }

    @Post("/1")
    public void switch1(){
        ledHelper.switchState();
    }

    @Post("/2")
    public void switch2(){
        ledHelper2.switchState();
    }
}
