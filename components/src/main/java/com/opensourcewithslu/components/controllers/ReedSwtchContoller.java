package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.ReedSwitchHelper;
import com.opensourcewithslu.outputdevices.LEDHelper;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/reedSwitch")
public class ReedSwtchContoller {
    private final Logger log = LoggerFactory.getLogger(ReedSwtchContoller.class);
    private final ReedSwitchHelper reedSwitchHelper;
    private final LEDHelper ledHelper;

    public ReedSwtchContoller(@Named("reed-switch-input") DigitalInput reedSwitch,
                              @Named("led1") DigitalOutput led) {
        this.reedSwitchHelper = new ReedSwitchHelper(reedSwitch);
        this.ledHelper = new LEDHelper(led);
    }

    @Get("/enable")
    public void enableReedSwitch() {
        reedSwitchHelper.addEventListener(e -> {
            try {
                if (reedSwitchHelper.isDetected) {
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
    public void disableReedSwitch() {
        reedSwitchHelper.removeEventListener();
    }
}

