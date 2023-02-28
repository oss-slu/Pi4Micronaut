package com.opensourcewithslu.outputdevices;

import com.pi4j.Pi4J;
import com.pi4j.catalog.components.LcdDisplay;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2CConfig;
import io.micronaut.context.annotation.Prototype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pi4j.crowpi.components.LcdDisplayComponent;

@Prototype
public class LCD1602Helper {
    private static final Logger log = LoggerFactory.getLogger(LEDHelper.class);

    private final LcdDisplay lcdDisplay;

    public LCD1602Helper(I2CConfig i2CConfig, Context pi4jContext){
        this.lcdDisplay = new LcdDisplay(pi4jContext, 4, 20);
        log.info("In constructor");
        lcdDisplay.setDisplayBacklight(true);
        log.info("backlight should be on");
    }

    public void writeText(String text){
        log.info("writing");
        lcdDisplay.displayText(text);
    }
}