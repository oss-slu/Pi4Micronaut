package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.LCD1602Helper;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2CConfig;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Named;

//tag::ex[]
@Controller("/lcd")
public class lcdController {
    private final LCD1602Helper lcdHelper;

    public lcdController(@Named("lcd")I2CConfig i2cConfig, Context pi4jContext){
        this.lcdHelper = new LCD1602Helper(i2cConfig, pi4jContext);
    }

    @Get("/write/{text}")
    public void writeData(String text){
        lcdHelper.writeText(text);
    }

    @Get("/write/{text}/{line}")
    public void writeDataAtLine(String text, int line){
        lcdHelper.writeTextAtLine(text, line);
    }

    @Get("/backlight/on")
    public void backlightOn(){
        lcdHelper.setBackLight(true);
    }

    @Get("/backlight/off")
    public void backlightOff(){
        lcdHelper.setBackLight(false);
    }

    @Get("/clear/all")
    public void clearDisplay(){
        lcdHelper.clearDisplay();
    }

    @Get("/clear/{line}")
    public void clearLine(int line){
        lcdHelper.clearLine(line);
    }
}
//end::ex[]