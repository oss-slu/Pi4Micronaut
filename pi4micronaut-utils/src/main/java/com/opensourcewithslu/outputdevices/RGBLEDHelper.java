package com.opensourcewithslu.outputdevices;

import com.opensourcewithslu.utilities.MultiPinConfiguration;
import com.pi4j.io.pwm.Pwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The RBGLEDHelper class handles all interactions with a RGB LED.
 */
public class RGBLEDHelper {
    private static final Logger log = LoggerFactory.getLogger(RGBLEDHelper.class);

    private final Pwm red;

    private final Pwm green;

    private final Pwm blue;

    /**
     * The RGBLEDHelper constructor.
     * @param pwm A {@link  com.opensourcewithslu.utilities.MultiPinConfiguration} Object.
     */
    //tag::const[]
    public RGBLEDHelper(MultiPinConfiguration pwm)
    //end::const[]
    {
        log.info("Init rgb");
        Pwm[] pwms = (Pwm[]) pwm.getComponents();
        this.red = pwms[0];
        this.green = pwms[1];
        this.blue = pwms[2];
    }

    /**
     * Sets the color of the LED based of inputted RGB values. Set with a default frequency of 200 Hertz.
     * @param colors RGB values in an array. [Red,Green,Blue].
     */
    //tag::method[]
    public void setColor(int[] colors)
    //end::method[]
    {
        red.on(colors[0], 200);
        green.on(colors[1], 200);
        blue.on(colors[2], 200);
    }

    /**
     * Sets the color of the LED using the array of RGB values and an array of frequencies.
     * @param colors RGB values in an array. [Red,Green,Blue].
     * @param frequency Frequency values(in Hertz) for the corresponding RGB value. [Red frequency, Green frequency, Blue frequency]
     */
    //tag::method[]
    public void setColor(int[] colors, int[] frequency)
    //end::method[]
    {
        log.info("setting the colors and frequency via a list");
        red.on(colors[0], frequency[0]);
        green.on(colors[1], frequency[1]);
        blue.on(colors[2], frequency[2]);
    }

    /**
     * Setting the color of the LED using a hexadecimal value. Default frequency of 200 Hertz is used.
     * @param hex Hexadecimal number optionally prefixed by 0x.
     */
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


    /**
     *  Setting the color of the LED using a hexadecimal value and an array of frequencies.
     * @param hex Hexadecimal number optionally prefixed by 0x.
     * @param frequency Frequency values(in Hertz) for the corresponding RGB value. [Red frequency, Green frequency, Blue frequency]
     */
    //tag::method[]
    public void setColorHex(String hex, int[] frequency)
    //end::method[]
    {
        log.info("Setting the color via Hex and a list of frequencies(RGB)");
        // hex splitting into rbg int values
        int r = (Integer.decode(hex) & 0xFF0000) >> 16;
        int g = (Integer.decode(hex) & 0xFF00) >> 8;
        int b = (Integer.decode(hex) & 0xFF);

        red.on(r, frequency[0]);
        green.on(g, frequency[1]);
        blue.on(b, frequency[2]);
    }

    /**
     * Sets the red value of the LED. Default 200 Hertz frequency used.
     * @param red Integer value representing the red in the RGB value of the LED.
     */
    //tag::method[]
    public void setRed(int red)
    //end::method[]
    {
        log.info("Set red");
        this.red.on(red, 200);
    }

    /**
     * Sets the red value and frequency of the LED.
     * @param red Integer value representing the red in the RGB value of the LED.
     * @param frequency Frequency of the red value in Hertz.
     */
    //tag::method[]
    public void setRed(int red, int frequency)
    //end::method[]
    {
        log.info("set red and set frequency");
        this.red.on(red, frequency);
    }

    /**
     * Sets the blue value of the LED. Default 200 Hertz frequency used.
     * @param blue Integer value representing the blue in the RGB value of the LED.
     */
    //tag::method[]
    public void setBlue(int blue)
    //end::method[]
    {
        log.info("set blue");
        this.blue.on(blue, 200);
    }

    /**
     * Sets the blue value and frequency of the LED.
     * @param blue Integer value representing the blue in the RGB value of the LED.
     * @param frequency Frequency of the blue value in Hertz.
     */
    //tag::method[]
    public void setBlue(int blue, int frequency)
    //end::method[]
    {
        log.info("set blue and set frequency");
        this.blue.on(blue, frequency);
    }

    /**
     * Sets the green value of the LED. Default 200 Hertz frequency used.
     * @param green Integer value representing the green in the RGB value of the LED.
     */
    //tag::method[]
    public void setGreen(int green)
    //end::method[]
    {
        log.info("set green");
        this.green.on(green, 200);
    }

    /**
     *  Sets the green value and frequency of the LED.
     * @param green Integer value representing the green in the RGB value of the LED.
     * @param frequency Frequency of the green value in Hertz.
     */
    //tag::method[]
    public void setGreen(int green, int frequency)
    //end::method[]
    {
        log.info("Setting green color and its frequency");
        this.green.on(green, frequency);
    }

    /**
     * Turns off the RGB LED.
     */
    //tag::method[]
    public void ledOff()
    //end::method[]
    {
        log.info("Turning off RGB LED");
        this.red.off();
        this.green.off();
        this.blue.off();
    }

    /**
     * Turns on the RGB LED with default RGB values of 100,100,100 and frequencies of 200 Hertz.
     */
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
