package com.opensourcewithslu.outputdevices;

import com.pi4j.catalog.components.LcdDisplay;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2CConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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

        this.lcdDisplay = new LcdDisplay(pi4jContext, 2, 16, i2CConfig.bus(), i2CConfig.device());
        lcdDisplay.setDisplayBacklight(true);
        log.info("LCD is set up with 2 rows and 16 columns. Backlight is on by default");
    }

    /**
     * Writes a String to the display.
     * @param text String object.
     */
    //tag::method[]
    public void writeText(String text)
    //end::method[]
    {
        log.info("writing");
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
        log.info("writing on " + line + "line");
        lcdDisplay.displayText(text, line);
    }


    public void writeCharacter(char charvalue){
        lcdDisplay.writeCharacter(charvalue);
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
            log.info("backlight on");
            lcdDisplay.setDisplayBacklight(true);
        } else{
            log.info("backlight off");
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
        log.info("Clear display");
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

    /**
     * Turns off LCD Display. Works the same as backlight off, but a write or clear command will turn the display back on.
     */
    public void turnOff(){
        lcdDisplay.off();
    }

    /// Below methods do not work

    /**
     * Moves the cursor to
     */
    public void moveCursorHome(){
        lcdDisplay.moveCursorHome();
    }

    /**
     * Moves cursor to the left.
     */
    public void moveCursorLeft(){
        lcdDisplay.moveCursorLeft();
    }

    /**
     * Moves crusor to the right.
     */
    public void moveCursorRight(){
        lcdDisplay.moveCursorRight();
    }

    /**
     * Moves the display left
     */
    public void moveDisplayLeft(){
        lcdDisplay.moveDisplayLeft();
    }

    /**
     * Move the display right
     */
    public void moveDisplayRight(){
        lcdDisplay.moveDisplayRight();
    }

    /**
     * Sets the cursor to blink
     * @param blink Set True to set the cursor to blink.
     */
    public void setCursorBlinking(boolean blink){
        lcdDisplay.setCursorBlinking(blink);
    }

    /**
     * Moves the cursor to the inputted display
     * @param line The line to move the cursor to.
     */
    public void setCursorToLine(int line){
        lcdDisplay.setCursorToLine(line);
    }

    /**
     * Sets the cursor to the position and line specified.
     * @param digit The position starting at 1 at the left most position. 16 is the right most position.
     * @param line The line to move the cursor to.
     */
    public void setCursorToPosition(int digit, int line){
        lcdDisplay.setCursorToPosition(digit,line);
    }

    /**
     * Sets the cursor to show or not show
     * @param show True to set the cursor to visible
     */
    public void setCursorVisibility(boolean show){
        lcdDisplay.setCursorVisibility(show);
    }

//    /**
//     * Creates a TBD
//     * @param location The position on the current line to place the character.
//     * @param character The character represented by a byte array.
//     */
//    public void createCharacter(int location,byte [] character){
//        lcdDisplay.createCharacter(location,character);
//    }




}
