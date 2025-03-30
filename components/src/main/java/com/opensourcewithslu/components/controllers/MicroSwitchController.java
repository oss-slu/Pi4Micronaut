package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.MicroSwitchHelper;
import com.opensourcewithslu.outputdevices.LEDHelper;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//tag::ex[]
@Controller("/microSwitch")
public class MicroSwitchController {
    private static final Logger log = LoggerFactory.getLogger(MicroSwitchController.class);
    private final MicroSwitchHelper microSwitchHelper;
    private final LEDHelper ledHelper1;
    private final LEDHelper ledHelper2;

    public MicroSwitchController(@Named("micro-switch") DigitalInput microSwitch,
                                 @Named("led") DigitalOutput led1,
                                 @Named("led2") DigitalOutput led2) {
        this.microSwitchHelper = new MicroSwitchHelper(microSwitch);
        this.ledHelper1 = new LEDHelper(led1);
        this.ledHelper2 = new LEDHelper(led2);
    }
    //enables the micro switch. The LEDs will switch states as the switch is pressed
    @Get("/enable")
    public void enableMicroSwitch() {
        microSwitchHelper.addEventListener(e -> {
            try {
                if (microSwitchHelper.isPressed) {
                    ledHelper1.ledOff();
                    ledHelper2.ledOn();
                } else {
                    ledHelper1.ledOn();
                    ledHelper2.ledOff();
                }
            } catch (Exception ex) {
                log.error("Error switching LED state", ex);
            }
        });
    }
    //disable the micro switch
    @Get("/disable")
    public void disableMicroSwitch() {
        microSwitchHelper.removeEventListener();
    }
}
//end::ex[]