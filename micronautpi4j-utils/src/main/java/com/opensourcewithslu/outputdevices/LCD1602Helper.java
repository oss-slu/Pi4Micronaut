package com.opensourcewithslu.outputdevices;

import com.pi4j.Pi4J;
import com.pi4j.catalog.components.LcdDisplay;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2CConfig;
import io.micronaut.context.annotation.Prototype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pi4j.crowpi.components.LcdDisplayComponent;


/**
 * This helper class, LCD1602Helper, is for controlling and interacting with an I2C LCD1602 display.
 */
public class LCD1602Helper {
    private static final Logger log = LoggerFactory.getLogger(LEDHelper.class);

    private final LcdDisplay lcdDisplay;

    /**
     * The LCD1602Helper constructor.
     * @param i2CConfig Unused parameter.
     * @param pi4jContext Context used to create LCD display object.
     */
    //tag::const[]
    public LCD1602Helper(I2CConfig i2CConfig, Context pi4jContext)
    //end::const[]
    {
        // The i2CConfig should be used to define the rows and columns, not hard coded.
        this.lcdDisplay = new LcdDisplay(pi4jContext, 4, 16);
        lcdDisplay.setDisplayBacklight(true);
        log.trace("LCD is set up with 4 rows and 16 columns. Backlight is on by default");
    }

    /**
     * Writes a String to the display.
     * @param text String object.
     */
    //tag::method[]
    public void writeText(String text)
    //end::method[]
    {
        log.trace("writing");
        lcdDisplay.displayText(text);
    }

    /**
     *  Writes a String to the defined line.
     * @param text String object to be displayed.
     * @param line Line on which the String is written.
     */
    //tag::method[]
    public void writeTextAtLine(String text, int line)
    //end::method[]
    {
        log.trace("writing on " + line + "line");
        lcdDisplay.displayText(text, line);
    }

    /**
     * Setting the backlight state of the LCD based off the boolean input. If true, then the backlight is set as on.
     * @param state  Boolean input to determine backlight state.
     */
    //tag::method[]
    public void setBackLight(boolean state)
    //end::method[]
    {
        log.info("changing backlight");

        if (state){
            log.trace("backlight on");
            lcdDisplay.setDisplayBacklight(true);
        } else{
            log.trace("backlight off");
            lcdDisplay.setDisplayBacklight(false);
        }
    }

    /**
     * Clears the display of text.
     */
    //tag::method[]
    public void clearDisplay()
    //end::method[]
    {
        log.trace("Clear display");
        lcdDisplay.clearDisplay();
    }


    /**
     * Clears the text of the specified line.
     * @param line The line of which text will be cleared.
     */
    //tag::method[]
    public void clearLine(int line)
    //end::method[]
    {
        lcdDisplay.clearLine(line);
    }
}
