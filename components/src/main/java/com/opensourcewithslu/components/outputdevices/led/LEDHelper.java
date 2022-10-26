package com.opensourcewithslu.components.outputdevices.led;

import com.pi4j.io.gpio.digital.DigitalOutput;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class LEDHelper {
    private static final Logger log = LoggerFactory.getLogger(LEDHelper.class);

    private final DigitalOutput ledOutput;

    public LEDHelper(@Named("led") DigitalOutput ledOutput) {
        this.ledOutput = ledOutput;
    }

    public void ledOn() {
        if (ledOutput.isLow()) {
            log.debug("Turning on LED");
            ledOutput.high();
        }
    }

    public void ledOff() {
        if (ledOutput.isHigh()) {
            log.debug("Turning off LED");
            ledOutput.low();
        }
    }
}
