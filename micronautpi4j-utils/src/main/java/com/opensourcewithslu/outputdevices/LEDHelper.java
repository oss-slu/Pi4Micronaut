package com.opensourcewithslu.outputdevices;
import com.pi4j.io.gpio.digital.DigitalOutput;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class LEDHelper extends OutputDevice {
    private static final Logger log = LoggerFactory.getLogger(LEDHelper.class);

    private final DigitalOutput ledOutput;

    public LEDHelper(@Named("led") DigitalOutput ledOutput) {
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
