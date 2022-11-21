package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.PushButtonHelper;
import com.opensourcewithslu.inputdevices.SlideSwitchHelper;
import com.opensourcewithslu.outputdevices.LEDHelper;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/slideSwitch")
public class SlideSwitchController {
    private final SlideSwitchHelper slideSwitchHelper;
    private final LEDHelper ledHelper;

    public SlideSwitchController(SlideSwitchHelper slideSwitchHelper, LEDHelper ledHelper) {
        this.slideSwitchHelper = slideSwitchHelper;
        this.ledHelper = ledHelper;
    }

    @Get("/init")
    public void initController(){
        slideSwitchHelper.addEventListener(e ->{
            ledHelper.switchState();
        });
    }

}
