package com.opensourcewithslu.inputdevices;

import com.pi4j.context.Context;
import com.pi4j.io.spi.Spi;
import com.pi4j.io.spi.SpiConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ADCConverterHelperTest {
    private static final Logger log = LoggerFactory.getLogger(ADCConverterHelperTest.class);

    private Context mockContext;
    private Spi mockSpi;
    private ADCConverterHelper adc;

    @BeforeEach
    public void setUp() {
        mockContext = Mockito.mock(Context.class);
        mockSpi = Mockito.mock(Spi.class);
        when(mockContext.create(any(SpiConfig.class))).thenReturn(mockSpi);
        adc = new ADCConverterHelper(mockContext, 0, 0);
    }

    @Test
    public void testInitializeADC_createsSpiConfig() {
        verify(mockContext).create(any(SpiConfig.class));
        log.info("Tested SPI config creation");
    }

    @Test
    public void testReadValueValidChannel() throws Exception {
        // Mock the transfer(byte[], byte[]) method
        doAnswer(invocation -> {
            byte[] txBuffer = invocation.getArgument(0);
            byte[] rxBuffer = invocation.getArgument(1);
            // Simulate ADC0834 response - 8-bit value in rxBuffer[1]
            rxBuffer[0] = 0x00;  // First byte unused
            rxBuffer[1] = (byte) 0x80;  // 8-bit value: 128 (decimal)
            return null;
        }).when(mockSpi).transfer(any(byte[].class), any(byte[].class));

        int value = adc.readValue(2);
        assertEquals(128, value, "ADC value should be 128 (8-bit)");
        log.info("Tested readValue for channel 2, got value: {}", value);
    }

    @Test
    public void testReadValueInvalidChannelLow() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> adc.readValue(-1));
        assertEquals("Channel must be between 0 and 3", exception.getMessage());
        log.info("Tested invalid channel -1, got expected exception");
    }

    @Test
    public void testReadValueInvalidChannelHigh() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> adc.readValue(4));
        assertEquals("Channel must be between 0 and 3", exception.getMessage());
        log.info("Tested invalid channel 4, got expected exception");
    }

    @Test
    public void testReadVoltageValid() throws Exception {
        // Mock the transfer method to return value 128
        doAnswer(invocation -> {
            byte[] txBuffer = invocation.getArgument(0);
            byte[] rxBuffer = invocation.getArgument(1);
            rxBuffer[0] = 0x00;
            rxBuffer[1] = (byte) 0x80;  // 128 in decimal
            return null;
        }).when(mockSpi).transfer(any(byte[].class), any(byte[].class));

        double voltage = adc.readVoltage(1, 3.3);
        // Expected: (128 / 255.0) * 3.3 = 1.654...
        assertEquals(3.3 * (128.0 / 255.0), voltage, 0.001, "Voltage should be correctly calculated (8-bit)");
        log.info("Tested readVoltage for channel 1, got voltage: {}V", voltage);
    }

    @Test
    public void testReadVoltageInvalidReference() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> adc.readVoltage(0, 0));
        assertEquals("Reference voltage must be positive", exception.getMessage());
        log.info("Tested invalid reference voltage 0, got expected exception");
    }

    @Test
    public void testSpiCommunicationFailure() throws Exception {
        // Mock transfer to throw exception
        doThrow(new RuntimeException("SPI error")).when(mockSpi).transfer(any(byte[].class), any(byte[].class));
        
        Exception exception = assertThrows(RuntimeException.class, () -> adc.readValue(0));
        assertTrue(exception.getMessage().contains("SPI"), "Expected SPI-related error");
        log.info("Tested SPI failure, got exception: {}", exception.getMessage());
    }
}