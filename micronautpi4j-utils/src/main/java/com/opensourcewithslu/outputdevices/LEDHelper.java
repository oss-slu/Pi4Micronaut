package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.digital.DigitalOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LEDHelper {
    private static final Logger log = LoggerFactory.getLogger(LEDHelper.class);

    private final DigitalOutput ledOutput;

    //tag::const[]
    public LEDHelper(DigitalOutput ledOutput)
    //end::const[]
    {
        this.ledOutput = ledOutput;
    }

    //tag::method[]
    public void ledOn()
    //end::method[]
    {
        if (ledOutput.isLow()) {
            log.info("Turning on LED");
            ledOutput.high();
        }
    }

    //tag::method[]
    public void ledOff()
    //end::method[]
    {
        if (ledOutput.isHigh()) {
            log.info("Turning off LED");
            ledOutput.low();
        }
    }

    //tag::method[]
    public void switchState()
    //end::method[]
    {
        if(ledOutput.isHigh()){
            ledOff();
        }
        else{
            ledOn();
        }
    }
}
