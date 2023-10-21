package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.PushButtonHelper;
import com.opensourcewithslu.outputdevices.LEDHelper;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

//tag::ex[]
@Controller("/pushButton")
public class PushButtonController {
    private final PushButtonHelper pushButtonHelper;
    private final LEDHelper ledHelper;

    public PushButtonController(@Named("button-input-3") DigitalInput pushButton1,
                                @Named("led") DigitalOutput led1) {
        this.pushButtonHelper = new PushButtonHelper(pushButton1);
        this.ledHelper = new LEDHelper(led1);
    }

    @Get("/init")
    public void initController(){
        pushButtonHelper.addEventListener(e ->{
            if(pushButtonHelper.isPressed){
                ledHelper.switchState();
            }
        });
    }
}
//end::ex[]