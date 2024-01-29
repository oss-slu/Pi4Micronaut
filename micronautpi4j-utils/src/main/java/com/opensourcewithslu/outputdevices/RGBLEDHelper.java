package com.opensourcewithslu.outputdevices;

import com.opensourcewithslu.utilities.MultipinConfiguration;
import com.pi4j.io.pwm.Pwm;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class RGBLEDHelper {
    private static final Logger log = LoggerFactory.getLogger(RGBLEDHelper.class);

    private final Pwm red;
    private final Pwm green;
    private final Pwm blue;

    //tag::const[]
    public RGBLEDHelper(@Named("rgb-led")MultipinConfiguration pwm)
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
        red.on(colors[0], 200);
        green.on(colors[1], 200);
        blue.on(colors[2], 200);
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

    //tag::method[]
    public void setColorHex(String hex)
    //end::method[]
    {
        log.info("setting the color via hex");
        // hex splitting into rbg int values
        int r = (Integer.decode(hex) & 0xFF0000) >> 16;
        int g = (Integer.decode(hex) & 0xFF00) >> 8;
        int b = (Integer.decode(hex) & 0xFF);

        // no frequency input, default value 200
        red.on(r, 200);
        green.on(g, 200);
        blue.on(b, 200);
    }
    //tag::method[]
    public void setColorHex(String hex, int[] frequency)
    //end::method[]
    {
        log.info("setting the color and frequency via hex and int");
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
        log.info("Set red");
        this.red.on(red, 200);
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
        this.blue.on(blue, 200);
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
        this.green.on(green, 200);
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
        this.red.off();
        this.green.off();
        this.blue.off();
    }

    //tag::method[]
    public void ledOn()
    //end::method[]
    {
        log.info("turning on each LED pin and setting to 100");
        this.red.on(100, 200);
        this.green.on(100, 200);
        this.blue.on(100, 200);
    }
}
