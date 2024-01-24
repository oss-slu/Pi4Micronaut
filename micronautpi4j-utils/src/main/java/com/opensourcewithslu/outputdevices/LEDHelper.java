package com.opensourcewithslu.outputdevices;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Prototype;
import io.micronaut.inject.qualifiers.Qualifiers;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  The class LEDHelper contains methods that pertain to the control of a LED.
 *
 */
public class LEDHelper {
    private static final Logger log = LoggerFactory.getLogger(LEDHelper.class);

    private DigitalOutput ledOutput;

    /**
     *
     *
     * @param ledOutput An instance of a DigitalOutput object. @see <a href="https://pi4j.com/documentation/io-examples/digital-output/">DigitalOutput</a>
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
    public void deviceOn()
    //end::method[]
    {
        if (ledOutput.isLow()) {
            log.debug("Turning on LED");
            ledOutput.high();
        }
    }

    /**
     *  Turns off the LED by setting the DigitalOutput object to low.
     *
     */
    //tag::method[]
    public void deviceOff()
    //end::method[]
    {
        if (ledOutput.isHigh()) {
            log.debug("Turning off LED");
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
            deviceOff();
        }
        else{
            deviceOn();
        }
    }
}
