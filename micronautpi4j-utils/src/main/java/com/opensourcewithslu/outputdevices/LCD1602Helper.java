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

    //tag::const[]
    public LCD1602Helper(I2CConfig i2CConfig, Context pi4jContext)
    //end::const[]
    {
        this.lcdDisplay = new LcdDisplay(pi4jContext, 4, 16);
        lcdDisplay.setDisplayBacklight(true);
        log.info("LCD is set up with 4 rows and 16 columns. Backlight is on by default");
    }

    //tag::method[]
    public void writeText(String text)
    //end::method[]
    {
        log.info("writing");
        lcdDisplay.displayText(text);
    }

    //tag::method[]
    public void writeTextAtLine(String text, int line)
    //end::method[]
    {
        log.info("writing on " + line + "line");
        lcdDisplay.displayText(text, line);
    }

    //tag::method[]
    public void setBackLight(boolean state)
    //end::method[]
    {
        log.info("changing backlight");
        if (state){
            log.info("backlight on");
            lcdDisplay.setDisplayBacklight(true);
        } else{
            log.info("backlight off");
            lcdDisplay.setDisplayBacklight(false);
        }
    }

    //tag::method[]
    public void clearDisplay()
    //end::method[]
    {
        log.info("Clear display");
        lcdDisplay.clearDisplay();
    }

    //tag::method[]
    public void clearLine(int line)
    //end::method[]
    {
        log.info("Clear line " + line + " display");
        lcdDisplay.clearLine(line);
    }
}
