package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.TiltSwitchHelper;
import com.opensourcewithslu.outputdevices.LEDHelper;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

//tag::ex[]
@Controller("/tiltSwitch")
public class TiltSwitchController {

    private final TiltSwitchHelper tiltSwitchHelper;
    private final LEDHelper ledHelper;

    public TiltSwitchController(@Named("tilt-switch-input")DigitalInput tiltSwitch,
                                @Named("led")DigitalOutput led) {
        this.tiltSwitchHelper = new TiltSwitchHelper(tiltSwitch);
        this.ledHelper = new LEDHelper(led);
    }

    @Get("/enable")
    public void enableTiltSwitch() {
        tiltSwitchHelper.addEventListener(e -> {
            if (tiltSwitchHelper.isTilted) {
                ledHelper.ledOn();
            }
            else {
                ledHelper.ledOff();
            }
        });
    }

    @Get("/disable")
    public void disableTiltSwitch() {
        tiltSwitchHelper.removeEventListener();
    }
}
//end::ex[]
