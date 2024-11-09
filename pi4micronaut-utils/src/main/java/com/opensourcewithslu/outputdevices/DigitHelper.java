package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.digital.DigitalOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DigitHelper {
    private static final Logger log = LoggerFactory.getLogger(DigitHelper.class);

    private final DigitalOutput digitOutput;

    public DigitHelper(DigitalOutput digitOutput) {
        this.digitOutput = digitOutput;
    }

    public void digitOn() {
        if (digitOutput.isLow()) {
            log.info("Turning on Digit");
            digitOutput.high();
        }
    }

    public void digitOff() {
        if (digitOutput.isHigh()) {
            log.info("Turning off Digit");
            digitOutput.low();
        }
    }
}
