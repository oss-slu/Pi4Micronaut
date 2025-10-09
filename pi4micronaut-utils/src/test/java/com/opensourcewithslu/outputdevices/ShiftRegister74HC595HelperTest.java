package com.opensourcewithslu.outputdevices;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pi4j.io.spi.Spi;

public class ShiftRegister74HC595HelperTest {

    @Mock
    private Spi spi;

    private ShiftRegister74HC595Helper helper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        helper = new ShiftRegister74HC595Helper(spi);
    }

   @Test
    public void testShiftOut_ValidByte() throws IOException {
        byte value = 0x5A;
        helper.shiftOut(value);
        verify(spi, times(1)).write(value);
    }

    @Test
    public void testShiftOut_SpiException() throws IOException {
        byte value = 0x5A;
        doThrow(new RuntimeException("SPI error")).when(spi).write(value);
        IOException exception = assertThrows(IOException.class, () -> helper.shiftOut(value));
        assertTrue(exception.getMessage().contains("SPI write failed"));
    }

    @Test
    public void testClear() throws IOException {
        helper.clear();
        verify(spi, times(1)).write((byte) 0x00);
    }

    @Test
    public void testSetBit_ValidIndex() throws IOException {
        helper.setBit(3, true);
        verify(spi, times(1)).write((byte) 0x08);
        helper.setBit(3, false);
        verify(spi, times(1)).write((byte) 0x00);
    }

    @Test
    public void testSetBit_InvalidIndex() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> helper.setBit(8, true));
        assertEquals("bitIndex must be 0..7; got 8", exception.getMessage());
    }

    @Test
    public void testClearBit() throws IOException {
        helper.setBit(2, true); // Set bit 2 first
        verify(spi, times(1)).write((byte) 0x04);
        helper.clearBit(2);
        verify(spi, times(1)).write((byte) 0x00);
    }

    @Test
    public void testGetState() throws IOException {
        assertEquals(0x00, helper.getState());
        helper.setBit(1, true);
        assertEquals(0x02, helper.getState());
    }

}
