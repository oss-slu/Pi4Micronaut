package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.PushButtonHelper;
import com.opensourcewithslu.outputdevices.LEDHelper;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;

//tag::ex[]
@Controller("/pushButton")
public class PushButtonController {
    private static final Logger log = LoggerFactory.getLogger(PushButtonController.class); 
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
                try {
                    ledHelper.switchState();
                } catch (Exception ex) {
                    log.error("Failed to switch LED state.", ex);
                }
            }
        });
    }
}
//end::ex[]