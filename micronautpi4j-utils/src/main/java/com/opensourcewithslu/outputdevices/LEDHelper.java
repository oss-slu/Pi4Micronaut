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

@Singleton
public class LEDHelper {
    private static final Logger log = LoggerFactory.getLogger(LEDHelper.class);

    private final DigitalOutput ledOutput;

    //tag::const[]
    public LEDHelper(@Named("led")DigitalOutput led)
    //end::const[]
    {
        this.ledOutput = led;
    }

    //tag::method[]
    public void deviceOn() 
    //end::method[]
    {
        if (ledOutput.isLow()) {
            log.trace("Turning off LED");
            ledOutput.high();
        }
    }

    //tag::method[]
    public void deviceOff() 
    //end::method[]
    {
        if (ledOutput.isHigh()) {
            log.trace("Turning on LED");
            ledOutput.low();
        }
    }

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
