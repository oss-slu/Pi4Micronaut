package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.digital.DigitalOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SegmentHelper {
    private static Logger log = LoggerFactory.getLogger(SegmentHelper.class);

    private final DigitalOutput segmentOutput;

    //tag::const[]
    public SegmentHelper(DigitalOutput segmentOutput)
    //end::const[]
    {
        this.segmentOutput = segmentOutput;
    }

    //tag::method[]
    public void segmentOn()
    //end::method[]
    {
        if (segmentOutput.isLow()) {
            log.info("Turning on Segment");
            segmentOutput.high();
        }
    }
}
