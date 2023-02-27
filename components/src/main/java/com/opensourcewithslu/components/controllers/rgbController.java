package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.RGBLEDHelper;
import com.pi4j.io.pwm.Pwm;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

@Controller("/rgb")
public class rgbController {
    private final RGBLEDHelper rgbledHelper;

    public rgbController(@Named("red-pwm") Pwm red,
                         @Named("blue-pwm") Pwm blue,
                         @Named("green-pwm") Pwm green){
        this.rgbledHelper = new RGBLEDHelper(red, blue, green);
    }

    @Get("/setRed/{val}")
    public void setRed(String val){
        rgbledHelper.setRed(Integer.parseInt(val));
    }

    @Get("/setBlue/{val}")
    public void setBlue(String val){
        rgbledHelper.setBlue(Integer.parseInt(val));
    }

    @Get("/setGreen/{val}")
    public void setGreen(String val){
        rgbledHelper.setGreen(Integer.parseInt(val));
    }
}
