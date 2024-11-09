package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.digital.DigitalOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SegmentHelper {
    private static final Logger log = LoggerFactory.getLogger(SegmentHelper.class);

    private final DigitalOutput segmentOutput;

    public SegmentHelper(DigitalOutput segmentOutput) {
        this.segmentOutput = segmentOutput;
    }

    public void segmentOn() {
        if (segmentOutput.isLow()) {
            log.info("Turning on Segment");
            segmentOutput.high();
        }
    }

    public void segmentOff() {
        if (segmentOutput.isHigh()) {
            log.info("Turning off Segment");
            segmentOutput.low();
        }
    }
}
