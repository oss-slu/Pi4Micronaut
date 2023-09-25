package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.PushButtonHelper;
import com.opensourcewithslu.outputdevices.LEDHelper;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

//tag::ex[]
@Controller("/pushButton")
public class PushButtonController {
    private final PushButtonHelper pushButtonHelper;
    private final LEDHelper ledHelper;

    public PushButtonController(PushButtonHelper pushButtonHelper, LEDHelper ledHelper) {
        this.pushButtonHelper = pushButtonHelper;
        this.ledHelper = ledHelper;
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