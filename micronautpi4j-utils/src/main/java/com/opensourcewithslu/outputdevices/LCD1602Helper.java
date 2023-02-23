package com.opensourcewithslu.outputdevices;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2CConfig;
import io.micronaut.context.annotation.Prototype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pi4j.crowpi.components.LcdDisplayComponent;

@Prototype
public class LCD1602Helper {
    private static final Logger log = LoggerFactory.getLogger(LEDHelper.class);

    private final LcdDisplayComponent lcdComponet;

    public LCD1602Helper(I2CConfig i2CConfig){
        Context pi4jContext = Pi4J.newAutoContext();
        log.info("In constructor");
        this.lcdComponet = new LcdDisplayComponent(pi4jContext, i2CConfig.getBus(), i2CConfig.device());
        log.info("initializing lcd component");
        this.lcdComponet.initialize();
        log.info("inited");
    }

    public void writeText(String text){
        log.info("writing text");
        lcdComponet.writeText(text);
    }
}
