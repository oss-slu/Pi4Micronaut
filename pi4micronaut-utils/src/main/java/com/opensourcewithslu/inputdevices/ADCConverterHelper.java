package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ADCConverterHelper class is used to read digital values from the ADC0834, which converts analog signals to digital.
 */
public class ADCConverterHelper {
    private static final Logger log = LoggerFactory.getLogger(ADCConverterHelper.class);

    private final DigitalOutput cs;    // Chip Select
    private final DigitalOutput clk;   // Clock
    private final DigitalOutput din;   // Data In
    private final DigitalInput dout;   // Data Out

    /**
     * Constructor for ADCConverterHelper.
     *
     * @param cs  DigitalOutput for Chip Select
     * @param clk DigitalOutput for Clock
     * @param din DigitalOutput for Data In
     * @param dout DigitalInput for Data Out
     */
    public ADCConverterHelper(DigitalOutput cs, DigitalOutput clk, DigitalOutput din, DigitalInput dout) {
        this.cs = cs;
        this.clk = clk;
        this.din = din;
        this.dout = dout;
    }

    /**
     * Reads a value from the ADC0834.
     *
     * @return The converted digital value from the ADC0834.
     */
    public int readValue() {
        cs.low(); // Activate ADC
        sendStartBit();

        int digitalValue = 0;
        for (int i = 0; i < 8; i++) {
            clk.high();
            clk.low();
            if (dout.isHigh()) {
                digitalValue |= (1 << (7 - i)); // Construct the digital value bit by bit
            }
        }

        cs.high(); // Deactivate ADC
        log.info("ADC0834 Digital Value Read: {}", digitalValue);
        return digitalValue;
    }

    /**
     * Sends the start bit to initialize communication with ADC0834.
     */
    private void sendStartBit() {
        clk.high();
        din.high();
        clk.low();
    }
}
