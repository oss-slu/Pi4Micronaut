package com.opensourcewithslu.outputdevices;

import com.opensourcewithslu.inputdevices.PushButtonHelper;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmConfig;
import io.micronaut.context.annotation.Prototype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Prototype
public class RGBLEDHelper {
    private static final Logger log = LoggerFactory.getLogger(RGBLEDHelper.class);

    private final Pwm red;
    private final Pwm green;
    private final Pwm blue;

    public RGBLEDHelper(Pwm red, Pwm green, Pwm blue){
        log.info("Init rgb");
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public void setColor(int[] colors){
        red.on(colors[0], 50);
        green.on(colors[1], 50);
        blue.on(colors[2], 50);
    }

    public void setColor(int[] colors, int[] frequency){
        log.info("setting the colors and frequency via a list");
        red.on(colors[0], frequency[0]);
        green.on(colors[1], frequency[1]);
        blue.on(colors[2], frequency[2]);
    }

    public void setColorHex(String hex) {
        log.info("setting the color via hex");
        //input the hex splitting and frequency stuff
    }

    public void setColorHex(String hex, int frequency) {
        log.info("setting the color and frequency via hex and int");
        // input the hex splitting and frequency stuff
    }

    public void setRed(int red){
        log.info("Set red");
        this.red.on(red, 50);
    }

    public void setRed(int red, int frequency) {
        log.info("set red and set frequency");
        this.red.on(red, frequency);
    }

    public void setBlue(int blue){
        log.info("set blue");
        this.blue.on(blue, 50);
    }

    public void setBlue(int blue, int frequency) {
        log.info("set blue and set frequency");
        this.blue.on(blue, frequency);
    }

    public void setGreen(int green){
        log.info("set green");
        this.green.on(green, 50);
    }

    public void setGreen(int green, int frequency) {
        log.info("set green and set frequency");
        this.green.on(green, frequency);
    }

    public void ledOff() {
        log.info("Turning off all the LED pins");
        this.red.off();
        this.green.off();
        this.blue.off();
    }

    public void ledOn() {
        log.info("turning on each LED pin and setting to 100");
        this.red.on(100, 50);
        this.green.on(100, 50);
        this.blue.on(100, 50);
    }
}
