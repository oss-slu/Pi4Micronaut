package com.opensourcewithslu.components.inputdevices.pushbutton;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/pushButton")
public class PushButtonController {
    private final PushButtonHelper pushButtonHelper;

    public PushButtonController(PushButtonHelper pushButtonHelper) {
        System.out.println("Push Button Controller Notification");
        this.pushButtonHelper = pushButtonHelper;
    }

    @Get("/button")
    public int checkThing(){
        return 3;
    }
}

