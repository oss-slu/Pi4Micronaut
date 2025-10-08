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

 
    public ShiftRegister74HC595Helper(Spi spi) {
        this.spi = Objects.requireNonNull(spi, "spi must not be null");
        log.info("74HC595 helper initialized; state=0x00");
    }


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

   
    public void clear() throws IOException {
        shiftOut((byte) 0x00); 
        log.info("Shift register cleared");
    }

    // Method to set a specific bit
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

    
    public void clearBit(int bitIndex) throws IOException {
        setBit(bitIndex, false);
        log.info("Bit {} cleared", bitIndex);
    }

    
    private static String toHex(byte b) {
        return String.format("%02X", b & 0xFF);
    }
}