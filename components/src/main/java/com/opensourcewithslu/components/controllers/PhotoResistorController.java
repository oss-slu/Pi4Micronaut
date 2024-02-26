package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.PhotoResistorHelper;
import com.opensourcewithslu.outputdevices.LEDHelper;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Named;
import javax.validation.constraints.Positive;

@Controller("/photoResistor")
public class PhotoResistorController {
    private final LEDHelper ledHelper;

    private final PhotoResistorHelper photoResistorHelper;

    public PhotoResistorController(@Named("photo-resistor-input")DigitalInput photoResistorIN,
                                   @Named("photo-resistor-output")DigitalOutput photoResistorOUT,
                                   @Named("led")DigitalOutput led ) {
        this.photoResistorHelper = new PhotoResistorHelper(photoResistorIN, photoResistorOUT);
        this.ledHelper = new LEDHelper(led);

    }

    @Get("/enable")
    public int enableLightSensor(){
        photoResistorHelper.initialize();
        photoResistorHelper.addEventListener(e -> {
            if (photoResistorHelper.isDark) {
                ledHelper.ledOn();
            }
            else {
                ledHelper.ledOff();
            }
        });
        return photoResistorHelper.getDark();

    }

    @Get("/getDarkness")
    public int dark() {
        return photoResistorHelper.getDark();
    }

    @Get("/disable")
    public void disableLightSensor() {
        photoResistorHelper.removeEventListener();
    }

    @Post("/threshold/{i}")
    public String setThreshold(@Positive int i) {
        photoResistorHelper.setDarknessThreshold(i);
        return ("Darkness threshold set to "+ i + "\n");
    }

}