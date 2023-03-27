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
                         @Named("green-pwm") Pwm green,
                         @Named("blue-pwm") Pwm blue){
        this.rgbledHelper = new RGBLEDHelper(red, green, blue);
    }

    @Get("/setRed/{val}")
    public void setRed(String val){
        rgbledHelper.setRed(Integer.parseInt(val));
    }

    @Get("/setRed/{val},{frequency}")
    public void setRed(String val, String frequency){
        rgbledHelper.setRed(Integer.parseInt(val), Integer.parseInt(frequency));
    }

    @Get("/setBlue/{val}")
    public void setBlue(String val){
        rgbledHelper.setBlue(Integer.parseInt(val));
    }

    @Get("/setBlue/{val},{frequency}")
    public void setBlue(String val, String frequency){
        rgbledHelper.setBlue(Integer.parseInt(val), Integer.parseInt(frequency));
    }

    @Get("/setGreen/{val}")
    public void setGreen(String val){
        rgbledHelper.setGreen(Integer.parseInt(val));
    }

    @Get("/setGreen/{val},{frequency}")
    public void setGreen(String val, String frequency){
        rgbledHelper.setGreen(Integer.parseInt(val), Integer.parseInt(frequency));
    }

    @Get("/setColor/{redVal},{greenVal},{blueVal}")
    public void setColor(int redVal, int greenVal, int blueVal){
        int[] colors = new int[] {redVal, greenVal, blueVal};
        rgbledHelper.setColor(colors);
    }

    @Get("/setColor/{redVal},{greenVal},{blueVal},{frequency1},{frequency2},{frequency3}")
    public void setColor(int redVal, int greenVal, int blueVal, int frequency1, int frequency2, int frequency3){
        int[] colors = new int[] {redVal, greenVal, blueVal};
        int[] frequency = new int[] {frequency1, frequency2, frequency3};
        rgbledHelper.setColor(colors, frequency);
    }

    @Get("/ledOff")
    public void ledOff() {
        rgbledHelper.ledOff();
    }

    @Get("/ledOn")
    public void ledOn() {
        rgbledHelper.ledOn();
    }
}
