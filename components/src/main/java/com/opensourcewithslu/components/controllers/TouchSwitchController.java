package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.TouchSwitchHelper;
import com.opensourcewithslu.outputdevices.LEDHelper;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/touchSwitch")
public class TouchSwitchController {

    private final TouchSwitchHelper touchSwitchHelper;

    private final LEDHelper ledHelper;

    public TouchSwitchController(DigitalInput touchSwitch,
                                 DigitalOutput led) {
        this.touchSwitchHelper = new TouchSwitchHelper(touchSwitch);
        this.ledHelper = new LEDHelper(led);
    }

    @Get("/enable")
    public void initController(){
        touchSwitchHelper.addEventListener(e->{
            if(touchSwitchHelper.isTouched){
                ledHelper.switchState();
            }
            else if(touchSwitchHelper.isReleased){
                ledHelper.switchState();
            }
        });
    }
}
