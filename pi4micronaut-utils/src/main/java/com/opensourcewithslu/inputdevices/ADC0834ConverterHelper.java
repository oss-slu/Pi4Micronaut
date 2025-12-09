package com.opensourcewithslu.inputdevices;

import com.pi4j.io.spi.Spi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ADC0834ConverterHelper class interfaces with the ADC0834 analog-to-digital converter using SPI.
 * It provides methods to read digital values and voltages from the specified channel.
 */
public class ADC0834ConverterHelper {
    private static final Logger log = LoggerFactory.getLogger(ADC0834ConverterHelper.class);
    private final Spi spi;

    /**
     * Constructor for ADC0834ConverterHelper.
     * @param spi The SPI interface.
     */
    public ADC0834ConverterHelper(Spi spi) {
        this.spi = spi;
        log.info("ADC0834ConverterHelper initialized with SPI");
    }

    /**
     * Reads the digital value from the specified channel (0-3) of the ADC0834.
     * @param channel The ADC channel to read (0-3).
     * @return The 8-bit digital value (0-255).
     * @throws IllegalArgumentException if channel is invalid.
     */
    public int readValue(int channel) {
        if (channel < 0 || channel > 3) {
            log.error("Invalid channel: {}. Must be 0-3.", channel);
            throw new IllegalArgumentException("Channel must be between 0 and 3");
        }

        // ADC0834 control byte: Start bit (1), single-ended (1), channel select (2 bits), reserved (0)
        byte controlByte = (byte) (0b10000000 | (channel << 5));
        byte[] txBuffer = new byte[] { controlByte, 0 }; // Second byte for clocking out data
        byte[] rxBuffer = new byte[2];

        try {
            spi.transfer(txBuffer, rxBuffer);
            int digitalValue = rxBuffer[1] & 0xFF; // 8-bit result
            log.info("Read channel {}: raw value = {}", channel, digitalValue);
            return digitalValue;
        } catch (Exception e) {
            log.error("Failed to read from ADC0834 on channel {}: {}", channel, e.getMessage());
            throw new RuntimeException("SPI communication error", e);
        }
    }

    /**
     * Reads the voltage from the specified channel using the provided reference voltage.
     * @param channel The ADC channel to read (0-3).
     * @param referenceVoltage The reference voltage (e.g., 3.3V or 5.0V).
     * @return The measured voltage.
     * @throws IllegalArgumentException if channel or referenceVoltage is invalid.
     */
    public double readVoltage(int channel, double referenceVoltage) {
        if (referenceVoltage <= 0) {
            log.error("Invalid reference voltage: {}. Must be positive.", referenceVoltage);
            throw new IllegalArgumentException("Reference voltage must be positive");
        }

        int rawValue = readValue(channel);
        double voltage = (rawValue / 255.0) * referenceVoltage;
        log.info("Channel {} voltage: {}V (raw = {}, ref = {}V)", channel, voltage, rawValue, referenceVoltage);
        return voltage;
    }
}
