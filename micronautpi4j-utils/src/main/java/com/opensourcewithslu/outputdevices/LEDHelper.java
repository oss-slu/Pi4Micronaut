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


@Prototype
public class LEDHelper {
    private static final Logger log = LoggerFactory.getLogger(LEDHelper.class);

    private DigitalOutput ledOutput;

    public LEDHelper(DigitalOutput ledOutput){
        this.ledOutput = ledOutput;
    }

    public void deviceOn() {
        if (ledOutput.isLow()) {
            log.info("Turning off LED");
            ledOutput.high();
        }
    }

    public void deviceOff() {
        if (ledOutput.isHigh()) {
            log.info("Turning on LED");
            ledOutput.low();
        }
    }

    public void switchState(){
        if(ledOutput.isHigh()){
            deviceOff();
        }
        else{
            deviceOn();
        }
    }
}
