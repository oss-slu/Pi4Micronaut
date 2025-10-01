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
public class ReedSwitchContoller {
    private final Logger log = LoggerFactory.getLogger(ReedSwitchContoller.class);
    private final ReedSwitchHelper reedSwitchHelper;
    private final LEDHelper ledHelper3;
    private final LEDHelper ledHelper4;

    public ReedSwitchContoller(@Named("reed-switch-input") DigitalInput reedSwitch,
                              @Named("led3") DigitalOutput led3,
                              @Named("led4") DigitalOutput led4) {
        this.reedSwitchHelper = new ReedSwitchHelper(reedSwitch);
        this.ledHelper3 = new LEDHelper(led3);
        this.ledHelper4 = new LEDHelper(led4);
    }

    @Get("/enable")
    public void enableReedSwitch() {
        reedSwitchHelper.addEventListener(e -> {
            try {
                if (reedSwitchHelper.isDetected) {
                    ledHelper3.ledOn();
                    ledHelper4.ledOff();
                } else {
                    ledHelper3.ledOff();
                    ledHelper4.ledOn();
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

