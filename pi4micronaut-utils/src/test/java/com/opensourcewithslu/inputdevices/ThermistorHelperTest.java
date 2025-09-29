package com.opensourcewithslu.inputdevices;

import com.pi4j.context.Context;
import com.pi4j.io.spi.Spi;
import com.pi4j.io.spi.SpiConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ThermistorHelperTest {

    private static final Logger log = LoggerFactory.getLogger(ThermistorHelperTest.class);

        
    private Context mockContext;
    private Spi mockSpi;
    private ThermistorHelper thermistorHelper;
    
    @BeforeEach
    void setUp() {
        mockContext = mock(Context.class);
        mockSpi = mock(Spi.class);

        // Mock Pi4J to return our Spi mock
        when(mockContext.create(any(SpiConfig.class))).thenReturn(mockSpi);

        thermistorHelper = new ThermistorHelper(mockContext);
        
    }

    @Test
    void testInitializeADC_createsSpiConfig() {
        // Verify that spi was created with spiconfig
        verify(mockContext).create(any(SpiConfig.class));
    }

    @Test 
    void testReadADCValue_successfulRead() throws Exception {
        ThermistorHelper spyHelper = spy(thermistorHelper);

        // Replace spi field with our mock
        doReturn(mockSpi).when(spyHelper).getSpi();

        // Mock spi.read() to fill buffer with fake ADC response
        doAnswer(invocation -> {
            byte[] buffer = invocation.getArgument(0);
            buffer[0] = 0x00;         // unused
            buffer[1] = 0x02;         // upper 2 bits (binary 10)
            buffer[2] = (byte) 0xAB;  // lower 8 bits (171)
            return null;
        }).when(mockSpi).read(any(byte[].class));
        double adcValue = spyHelper.readADCValue();
        log.info("Test successful ADC read, got value: {}", adcValue);
        assertEquals(683.0, adcValue, "ADC value should be correctly decoded");
    }     
    @Test
    void testReadADCValue_failureReturnsMinusOne() throws Exception {
        ThermistorHelper spyHelper = spy(thermistorHelper);

        
        doReturn(mockSpi).when(spyHelper).getSpi();

        
        doThrow(new RuntimeException("SPI failure")).when(mockSpi).read(any(byte[].class));
            double adcValue = spyHelper.readADCValue();
            assertEquals(-1.0, adcValue, "On failure, readADCValue should return -1");
    }
            
        
    @Test
    void testGetResistanceConversion() {
        ThermistorHelper spyHelper = spy(thermistorHelper);
        
        doReturn(512.0).when(spyHelper).readADCValue();
        double resistance = spyHelper.getResistance();
        // Expected formula result 
        double voltage = (512.0 / 1023.0) * 3.3;
        double expectedResistance = (10000 * (3.3 - voltage)) / voltage;
        assertEquals(expectedResistance, resistance, 0.01, "Resistance should be calculated correctly");
    }

    @Test
    void testGetTemperatureInCelsiusus() {
        ThermistorHelper spyHelper = spy(thermistorHelper);
        // Mock resistance to predict Celsius
        doReturn(10000.0).when(spyHelper).getResistance();
        double expectedCelsius = 1.0 /
                (0.001129148 + 0.000234125 * Math.log(10000.0)
                        + 0.0000000876741 * Math.pow(Math.log(10000.0), 3))
                - 273.15;
        double actual = spyHelper.getTemperatureInCelsius();
        assertEquals(expectedCelsius, actual, 0.01, "Temperature in Celsius should be correct");
    }

    @Test
    void testGetTemperatureInFahrenheit() {
        ThermistorHelper spyHelper = spy(thermistorHelper);
    // Mock Celsius conversion
        doReturn(25.0).when(spyHelper).getTemperatureInCelsius();
        // expected answer is 25 * 9/5 + 32 = 77
        double fahrenheit = spyHelper.getTemperatureInFahrenheit();
        assertEquals(77.0, fahrenheit, 0.01, "Fahrenheit should be Celsius * 9/5 + 32");
    }
}