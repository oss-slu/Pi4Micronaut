package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.PushButtonHelper;
import com.opensourcewithslu.outputdevices.LEDHelper;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/multi")
public class MultiCompController {
    private static final Logger log = LoggerFactory.getLogger(MultiCompController.class);

    private final LEDHelper ledHelper;

    private final LEDHelper ledHelper2;

    private final PushButtonHelper button1;

    private final PushButtonHelper button2;

    public MultiCompController(@Named("led") DigitalOutput led1,
                               @Named("led2") DigitalOutput led2,
                               @Named("button-input-1")DigitalInput button1,
                               @Named("button-input-2") DigitalInput button2) {
        this.ledHelper = new LEDHelper(led1);
        this.ledHelper2 = new LEDHelper(led2);
        this.button1 = new PushButtonHelper(button1);
        this.button2 = new PushButtonHelper(button2);
    }

    @Post("/led1")
    public void switch1(){
        try {
            ledHelper.switchState();
        } catch (Exception e) {
            log.error("Failed to switch LED1 state", e);
        }
    }

    @Post("/led2")
    public void switch2(){
        try {
            ledHelper2.switchState();
        } catch (Exception e) {
            log.error("Failed to switch LED2 state", e);
        }
    }

    @Get("/button1")
    public void button1(){
        button1.addEventListener(e ->{
            log.info(String.valueOf(button1.isPressed));
            if(button1.isPressed){
                try {
                    ledHelper.switchState();
                } catch (Exception ex) {
                    log.error("Failed to switch LED1 state via Button1", ex);
                }
            }
        });
    }

    @Get("/button2")
    public void button2(){
        button2.addEventListener(e ->{
            if(button2.isPressed){
                try {
                    ledHelper2.switchState();
                } catch (Exception ex) {
                    log.error("Failed to switch LED2 state via Button1", ex);
                }
            }
        });
    }
}
