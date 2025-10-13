package com.opensourcewithslu.outputdevices;

import java.io.IOException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.spi.Spi;

public class ShiftRegister74HC595Helper {

    private static final Logger log = LoggerFactory.getLogger(ShiftRegister74HC595Helper.class);
    private final Spi spi;
    private byte state = 0x00;

    /**
     * Constructs a new ShiftRegister74HC595Helper with the specified SPI interface.
     *
     * Initializes the helper with a clean state (0x00) and sets up logging.
     *
     * 
     * @param spi the SPI interface to use for communication with the shift register
     * @throws NullPointerException if spi is null
     */
    public ShiftRegister74HC595Helper(Spi spi) {
        this.spi = Objects.requireNonNull(spi, "spi must not be null");
        log.info("74HC595 helper initialized; state=0x00");
    }

    /**
     * Shifts out a byte value to the 74HC595 shift register via SPI.
     *
     * This method writes the specified byte value to the shift register and
     * updates the internal state tracking. The operation is logged for debugging.
     *
     * @param value the byte value to shift out to the register
     * @throws IOException if the SPI write operation fails
     */
    public void shiftOut(byte value) throws IOException {
        try {
            spi.write(value); 
            this.state = value;
            log.info("Shifted out 0x{}", toHex(this.state));
        } catch (Exception e) {
            String msg = String.format("SPI write failed for 0x%s: %s", toHex(value), e.getMessage());
            log.error(msg, e);
            throw new IOException(msg, e);
        }
    }

    /**
     * Clears the shift register by setting all outputs to LOW (0x00).
     *
     * This is equivalent to calling {@code shiftOut((byte) 0x00)}.
     *
     * @throws IOException if the SPI write operation fails
     */
    public void clear() throws IOException {
        shiftOut((byte) 0x00); 
        log.info("Shift register cleared");
    }

    /**
     * Sets or clears a specific bit in the shift register.
     *
     * This method modifies the internal state by setting or clearing the specified
     * bit index (0-7), then shifts out the updated state to the register.
     *
     * @param bitIndex the index of the bit to modify (0-7, where 0 is LSB)
     * @param value {@code true} to set the bit to HIGH, {@code false} to set to LOW
     * @throws IllegalArgumentException if bitIndex is not in the range 0-7
     * @throws IOException if the SPI write operation fails
     */
    public void setBit(int bitIndex, boolean value) throws IOException {
        if (bitIndex < 0 || bitIndex > 7) {
            throw new IllegalArgumentException("bitIndex must be 0..7; got " + bitIndex);
        }
        if (value) {
            state |= (1 << bitIndex); 
        } else {
            state &= ~(1 << bitIndex);
        }
        shiftOut(state);
        log.info("Bit {} set to {}", bitIndex, value);
    }

    /**
     * Clears (sets to LOW) a specific bit in the shift register.
     *
     * This is a convenience method equivalent to calling {@code setBit(bitIndex, false)}.
     *
     * @param bitIndex the index of the bit to clear (0-7, where 0 is LSB)
     * @throws IllegalArgumentException if bitIndex is not in the range 0-7
     * @throws IOException if the SPI write operation fails
     */
    public void clearBit(int bitIndex) throws IOException {
        setBit(bitIndex, false);
        log.info("Bit {} cleared", bitIndex);
    }

    /**
     * Returns the current state of the shift register.
     *
     * This method returns the internally tracked state without performing
     * any SPI operations. The state reflects the last value written to
     * the shift register.
     *
     * @return the current state as a byte value
     */
    public byte getState() {
        return state;
    }

    /**
     * Converts a byte value to its hexadecimal string representation.
     *
     * This is a utility method used for logging purposes to display
     * byte values in a readable hexadecimal format.
     *
     * @param b the byte value to convert
     * @return a two-character uppercase hexadecimal string representation
     */
    private static String toHex(byte b) {
        return String.format("%02X", b & 0xFF);
    }
}