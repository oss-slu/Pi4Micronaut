package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.TiltSwitchHelper;
import com.opensourcewithslu.outputdevices.LEDHelper;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//tag::ex[]
@Controller("/tiltSwitch")
public class TiltSwitchController {
    private static final Logger log = LoggerFactory.getLogger(TiltSwitchController.class);
    private final TiltSwitchHelper tiltSwitchHelper; // controls the tilt switch
    private final LEDHelper ledHelper; // controls the LED

    public TiltSwitchController(@Named("tilt-switch-input")DigitalInput tiltSwitch,
                                @Named("led2")DigitalOutput led) {
        this.tiltSwitchHelper = new TiltSwitchHelper(tiltSwitch);
        this.ledHelper = new LEDHelper(led);
    }

    // The 'enable' method adds an event listener that listens for changes in the tilt switch
    // If it is tilted, the LED turns on and off otherwise
    @Get("/enable")
    public void enableTiltSwitch() {
        tiltSwitchHelper.addEventListener(e -> {
            try {
                if (tiltSwitchHelper.isTilted) {
                    ledHelper.ledOn();
                } else {
                    ledHelper.ledOff();
                }
            } catch (Exception ex) {
                log.error("Error switching LED state", ex);
            }
        });
    }

    // The disable method removes the event listener
    @Get("/disable")
    public void disableTiltSwitch() {
        tiltSwitchHelper.removeEventListener();
    }
}
//end::ex[]