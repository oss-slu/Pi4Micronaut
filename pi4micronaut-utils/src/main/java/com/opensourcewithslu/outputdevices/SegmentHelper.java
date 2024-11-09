package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.digital.DigitalOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SegmentHelper {
    private static final Logger log = LoggerFactory.getLogger(SegmentHelper.class);

    private final DigitalOutput sdi;
    private final DigitalOutput rclk;
    private final DigitalOutput srclk;

    public SegmentHelper(DigitalOutput sdi, DigitalOutput rclk, DigitalOutput srclk) {
        this.sdi = sdi;
        this.rclk = rclk;
        this.srclk = srclk;
    }

    public void shiftOut(int data) {
        try {
            for (int i = 0; i < 8; i++) {
                srclk.low();
                if ((data & (1 << (7 - i))) != 0) {
                    sdi.high();
                } else {
                    sdi.low();
                }
                srclk.high();
            }
            rclk.high();
            rclk.low();
            log.info("Shifted out data: " + Integer.toHexString(data));
        } catch (Exception e) {
            log.error("Error shifting out data", e);
        }
    }

    public void clear() {
        shiftOut(0x00);
    }
}
