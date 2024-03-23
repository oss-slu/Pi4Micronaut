package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.LCD1602Helper;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2CConfig;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import jakarta.inject.Named;

/**
 * Controller for managing LCD1602 display operations via HTTP requests.
 */
@Controller("/lcd")
public class lcdController {
    private final LCD1602Helper lcdHelper;

    public lcdController(@Named("lcd") I2CConfig i2cConfig, Context pi4jContext) {
        this.lcdHelper = new LCD1602Helper(i2cConfig, pi4jContext);
    }

    @Get("/write/{text}")
    public String writeData(@PathVariable String text) {
        lcdHelper.writeText(text);
        return "Text written to LCD: " + text + "\n";
    }

    @Get("/write/{text}/{line}")
    public String writeDataAtLine(@PathVariable String text, @PathVariable int line) {
        lcdHelper.writeTextAtLine(text, line);
        return "Text written to line " + line + ": " + text + "\n";
    }

    @Get("/write/{text}/{line}/{pos}")
    public String writeDataAtPos(@PathVariable String text, @PathVariable int line, @PathVariable int pos) {
        lcdHelper.displayTextAtPos(text, line, pos);
        return "Text written at line " + line + ", position " + pos + ": " + text + "\n";
    }

    @Get("/write/character/{charValue}")
    public String writeCharacter(@PathVariable char charValue) {
        lcdHelper.writeCharacter(charValue);
        return "Character '" + charValue + "' written to LCD\n";
    }

    @Get("/backlight/{state}")
    public String setBacklight(@PathVariable String state) {
        boolean isOn = "on".equalsIgnoreCase(state);
        lcdHelper.setBackLight(isOn);
        return "Backlight turned " + (isOn ? "on" : "off") + "\n";
    }

    @Get("/clear/all")
    public String clearDisplay() {
        lcdHelper.clearDisplay();
        return "Display cleared\n";
    }

    @Get("/clear/{line}")
    public String clearLine(@PathVariable int line) {
        lcdHelper.clearLine(line);
        return "Line " + line + " cleared\n";
    }

    @Get("/turnOff")
    public String turnOff() {
        lcdHelper.turnOff();
        return "Display turned off\n";
    }
}
