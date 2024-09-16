package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.PushButtonHelper;
import com.opensourcewithslu.outputdevices.LEDHelper;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;
import org.slf4j.Logger; // Added Logging Capability 
import org.slf4j.LoggerFactory; // Added Logging Capability

//tag::ex[]
@Controller("/pushButton")
public class PushButtonController {
    private static final Logger log = LoggerFactory.getLogger(PushButtonController.class); // Added LOG

    private final PushButtonHelper pushButtonHelper;

    private final LEDHelper ledHelper;

    public PushButtonController(@Named("button-input-3") DigitalInput pushButton,
                                @Named("led") DigitalOutput led) {
        this.pushButtonHelper = new PushButtonHelper(pushButton);
        this.ledHelper = new LEDHelper(led);
    }

    @Get("/init")
    public void initController(){
        pushButtonHelper.addEventListener(e ->{
            if(pushButtonHelper.isPressed){
                try { // Added try catch for runtime expection error avoidance
                    ledHelper.switchState();
                    log.info("LED state switched successfully.");
                } catch (Exception ex) {
                    log.error("Failed to switch LED state.", ex);
                }
            }
        });
    }
}
//end::ex[]