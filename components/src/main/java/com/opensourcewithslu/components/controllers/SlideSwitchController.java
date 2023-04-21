package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.PushButtonHelper;
import com.opensourcewithslu.inputdevices.SlideSwitchHelper;
import com.opensourcewithslu.outputdevices.LEDHelper;
import com.pi4j.io.gpio.digital.DigitalInput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

//tag::ex[]
@Controller("/slideSwitch")
public class SlideSwitchController {
    private final SlideSwitchHelper slideSwitchHelper;

    private final SlideSwitchHelper slideSwitchHelper2;

    public SlideSwitchController(@Named("slide-switch-input")DigitalInput slideSwitch, 
                                    @Named("slide-switch-input-2")DigitalInput slideSwitch2) {
        this.slideSwitchHelper = new SlideSwitchHelper(slideSwitch);
        this.slideSwitchHelper2 = new SlideSwitchHelper(slideSwitch2);
    }

    @Get("/switch1")
    public boolean checkSwitch1(){
        return slideSwitchHelper.isOn;
    }

    @Get("/switch2")
    public boolean checkSwitch2(){
        return slideSwitchHelper2.isOn;
    }

}
//end::ex[]
