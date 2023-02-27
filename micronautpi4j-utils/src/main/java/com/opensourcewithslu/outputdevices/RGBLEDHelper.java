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
    private final Pwm blue;
    private final Pwm green;

    public RGBLEDHelper(Pwm red, Pwm blue, Pwm green){
        log.info("Init rgb");
        this.red = red;
        this.blue = blue;
        this.green = green;
    }

    public void setColor(int[] colors){
        red.on(50, colors[0]);
        blue.on(50, colors[1]);
        green.on(50, colors[2]);
    }

    public void setRed(int red){
        log.info("Set red");
        this.red.on(red, 50);
    }

    public void setBlue(int blue){
        log.info("set blue");
        this.blue.on(blue, 50);
    }

    public void setGreen(int green){
        log.info("set green");
        this.green.on(green, 50);
    }
}
