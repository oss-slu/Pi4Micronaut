package com.opensourcewithslu.outputdevices;

import com.opensourcewithslu.inputdevices.PushButtonHelper;
import com.opensourcewithslu.utilities.MultipinConfiguration;
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

    //tag::const[]
    public RGBLEDHelper(MultipinConfiguration pwm)
    //end::const[]
    {
        log.info("Init rgb");
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

    //tag::method[]
    public void setRed(int red)
    //end::method[]
    {
        log.info("Set red");
        this.red.on(red, 50);
    }

    //tag::method[]
    public void setRed(int red, int frequency) 
    //end::method[]
    {
        log.info("set red and set frequency");
        this.red.on(red, frequency);
    }

    //tag::method[]
    public void setBlue(int blue)
    //end::method[]
    {
        log.info("set blue");
        this.blue.on(blue, 50);
    }

    //tag::method[]
    public void setBlue(int blue, int frequency) 
    //end::method[]
    {
        log.info("set blue and set frequency");
        this.blue.on(blue, frequency);
    }

    //tag::method[]
    public void setGreen(int green)
    //end::method[]
    {
        log.info("set green");
        this.green.on(green, 50);
    }

    //tag::method[]
    public void setGreen(int green, int frequency) 
    //end::method[]
    {
        log.info("set green and set frequency");
        this.green.on(green, frequency);
    }

    //tag::method[]
    public void ledOff()
    //end::method[]
    {
        log.info("Turning off all the LED pins");
        this.red.off();
        this.green.off();
        this.blue.off();
    }

    //tag::method[]
    public void ledOn() 
    //end::method[]
    {
        log.info("turning on each LED pin and setting to 100");
        this.red.on(100, 50);
        this.green.on(100, 50);
        this.blue.on(100, 50);
    }
}
