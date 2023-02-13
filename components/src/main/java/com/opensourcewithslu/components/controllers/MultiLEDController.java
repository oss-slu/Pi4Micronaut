package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.PushButtonHelper;
import com.opensourcewithslu.outputdevices.LEDHelper;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

@Controller("/led")
public class MultiLEDController {
    private LEDHelper ledHelper;
    private LEDHelper ledHelper2;

    public MultiLEDController(LEDHelper ledHelper, LEDHelper ledHelper2) {
        this.ledHelper = ledHelper;
        this.ledHelper.setBean("led");

        this.ledHelper2 = ledHelper2;
        this.ledHelper2.setBean("led2");
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
