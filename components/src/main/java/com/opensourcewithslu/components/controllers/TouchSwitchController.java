package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.TouchSwitchHelper;
import com.opensourcewithslu.outputdevices.LEDHelper;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//tag::ex[]
@Controller("/touchSwitch")
public class TouchSwitchController {
    private static final Logger log = LoggerFactory.getLogger(TouchSwitchController.class);
    private final TouchSwitchHelper touchSwitchHelper;
    private final LEDHelper ledHelper;

    public TouchSwitchController(@Named("touch-switch-input") DigitalInput touchSwitch,
                                 @Named("led2") DigitalOutput led) {
        this.touchSwitchHelper = new TouchSwitchHelper(touchSwitch);
        this.ledHelper = new LEDHelper(led);
    }

    @Get("/enable")
    public void enableTouchSwitch() {
        touchSwitchHelper.addEventListener(e -> {
            try {
                if (touchSwitchHelper.isTouched) {
                    ledHelper.ledOn();
                } else {
                    ledHelper.ledOff();
                }
            } catch (Exception ex) {
                log.error("Error switching LED state", ex);
            }
        });
    }

    @Get("/disable")
    public void disableTouchSwitch() {
        touchSwitchHelper.removeEventListener();
    }
}
//end::ex[]