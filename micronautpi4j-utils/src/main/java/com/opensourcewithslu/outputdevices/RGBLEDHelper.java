package com.opensourcewithslu.outputdevices;

import com.opensourcewithslu.inputdevices.PushButtonHelper;
import com.opensourcewithslu.utilities.MultipinConfiguration;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmConfig;
import io.micronaut.context.annotation.Prototype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RGBLEDHelper {
    private static final Logger log = LoggerFactory.getLogger(RGBLEDHelper.class);

    private final Pwm red;
    private final Pwm green;
    private final Pwm blue;

    //tag::const[]
    public RGBLEDHelper(MultipinConfiguration pwm)
    //end::const[]
    {
        log.trace("Init rgb");
        Pwm[] pwms = (Pwm[]) pwm.getComponents();
        this.red = pwms[0];
        this.green = pwms[1];
        this.blue = pwms[2];
    }

    //tag::method[]
    public void setColor(int[] colors)
    //end::method[]
    {
        red.on(colors[0], 50);
        green.on(colors[1], 50);
        blue.on(colors[2], 50);
    }

    //tag::method[]
    public void setColor(int[] colors, int[] frequency)
    //end::method[]
    {
        log.trace("setting the colors and frequency via a list");
        red.on(colors[0], frequency[0]);
        green.on(colors[1], frequency[1]);
        blue.on(colors[2], frequency[2]);
    }

    public void setColorHex(String hex) {
        log.trace("setting the color via hex");
        // hex splitting into rbg int values
        int r = (Integer.decode(hex) & 0xFF0000) >> 16;
        int g = (Integer.decode(hex) & 0xFF00) >> 8;
        int b = (Integer.decode(hex) & 0xFF);

        // no frequency input, default value 50
        red.on(r, 50);
        green.on(g, 50);
        blue.on(b, 50);
    }

    public void setColorHex(String hex, int[] frequency) {
        log.trace("setting the color and frequency via hex and int");
        // hex splitting into rbg int values
        int r = (Integer.decode(hex) & 0xFF0000) >> 16;
        int g = (Integer.decode(hex) & 0xFF00) >> 8;
        int b = (Integer.decode(hex) & 0xFF);

        red.on(r, frequency[0]);
        green.on(g, frequency[1]);
        blue.on(b, frequency[2]);
    }

    //tag::method[]
    public void setRed(int red)
    //end::method[]
    {
        log.trace("Set red");
        this.red.on(red, 50);
    }

    //tag::method[]
    public void setRed(int red, int frequency) 
    //end::method[]
    {
        log.trace("set red and set frequency");
        this.red.on(red, frequency);
    }

    //tag::method[]
    public void setBlue(int blue)
    //end::method[]
    {
        log.trace("set blue");
        this.blue.on(blue, 50);
    }

    //tag::method[]
    public void setBlue(int blue, int frequency) 
    //end::method[]
    {
        log.trace("set blue and set frequency");
        this.blue.on(blue, frequency);
    }

    //tag::method[]
    public void setGreen(int green)
    //end::method[]
    {
        log.trace("set green");
        this.green.on(green, 50);
    }

    //tag::method[]
    public void setGreen(int green, int frequency) 
    //end::method[]
    {
        log.trace("set green and set frequency");
        this.green.on(green, frequency);
    }

    //tag::method[]
    public void ledOff()
    //end::method[]
    {
        this.red.off();
        this.green.off();
        this.blue.off();
    }

    //tag::method[]
    public void ledOn() 
    //end::method[]
    {
        log.trace("turning on each LED pin and setting to 100");
        this.red.on(100, 50);
        this.green.on(100, 50);
        this.blue.on(100, 50);
    }
}
