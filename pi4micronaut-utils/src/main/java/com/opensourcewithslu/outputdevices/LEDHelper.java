package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.digital.DigitalOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 *  The class LEDHelper contains methods that pertain to the control of a LED.
 *
 */
public class LEDHelper {
    private static Logger log = LoggerFactory.getLogger(LEDHelper.class);

    private final DigitalOutput ledOutput;

    /**
     *
     * LEGHelper constructor.
     * @param ledOutput An instance of a Pi4J DigitalOutput object.
     */
    //tag::const[]
    public LEDHelper(DigitalOutput ledOutput)
    //end::const[]
    {
        this.ledOutput = ledOutput;
    }

    /**
     * Turns on the LED by setting the DigitalOutput object to high.
     *
     */
    //tag::method[]
    public void ledOn()
    //end::method[]
    {
        if (ledOutput.isLow()) {
            log.info("Turning on LED");
            ledOutput.high();
        }
    }

    /**
     *  Turns off the LED by setting the DigitalOutput object to low.
     *
     */
    //tag::method[]
    public void ledOff()
    //end::method[]
    {
        if (ledOutput.isHigh()) {
            log.info("Turning off LED");
            ledOutput.low();
        }
    }

    /**
     * Switches the state of the LED. If the LED is on, the LED is turned off and vice versa.
     *
     */
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

    /**
     *
     * @param duration blink will take the duration parameter and have the led
     *                 blink for that duration.
     *
     */

    public void blink (int duration) {

        ledOutput.blink(duration, TimeUnit.MILLISECONDS);
    }

    /**
     * Sets the logger object.
     * This method is intended for internal testing purposes only.
     *
     * @param log Logger object to set the logger to.
     */
    void setLog(Logger log) {
        this.log = log;
    }
}
