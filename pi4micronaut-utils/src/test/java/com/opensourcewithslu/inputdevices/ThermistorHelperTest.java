package com.opensourcewithslu.inputdevices;

import com.pi4j.context.Context;
import com.pi4j.io.spi.Spi;
import com.pi4j.io.spi.SpiConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ThermistorHelperTest {

    private static final Logger log = LoggerFactory.getLogger(ThermistorHelperTest.class);

    private int channel;
    private Spi mockSpi;
    private SpiConfig mockSpiConfig;
    private Context mockContext;
    private ThermistorHelper thermistorHelper;
    private ADC0834ConverterHelper adcHelper;
    
    @BeforeEach
    void setUp() {
        // Set channel to a valid value
        channel = 0;

        mockSpi = mock(Spi.class);
        mockSpiConfig = mock(SpiConfig.class);
        mockContext = mock(Context.class);
        when(mockContext.create(any(SpiConfig.class))).thenReturn(mockSpi);
        adcHelper = new ADC0834ConverterHelper(mockSpiConfig, mockContext);

        thermistorHelper = new ThermistorHelper(adcHelper);
    }

    @Test
    void testReadADCValue_failureReturnsMinusOne() throws Exception {
        ThermistorHelper spyHelper = spy(thermistorHelper);
        
        doReturn(mockSpi).when(spyHelper).getSpi();
        
        doThrow(new RuntimeException("SPI failure")).when(mockSpi).read(any(byte[].class));
    }
            
        
    @Test
    void testGetResistanceConversion() {
        ThermistorHelper spyHelper = spy(thermistorHelper);
        
        doReturn(512.0).when(spyHelper).readADCValue( channel );
        double resistance = spyHelper.getResistance( channel );
        // Expected formula result 
        double voltage = (512.0 / 1023.0) * 3.3;
        double expectedResistance = (10000 * (3.3 - voltage)) / voltage;
        assertEquals(expectedResistance, resistance, 0.01, "Resistance should be calculated correctly");
    }

    @Test
    void testGetTemperatureInCelsiusus() {
        ThermistorHelper spyHelper = spy(thermistorHelper);
        // Mock resistance to predict Celsius
        doReturn(10000.0).when(spyHelper).getResistance( channel );
        double expectedCelsius = 1.0 /
                (0.001129148 + 0.000234125 * Math.log(10000.0)
                        + 0.0000000876741 * Math.pow(Math.log(10000.0), 3))
                - 273.15;
        double actual = spyHelper.getTemperatureInCelsius( channel );
        assertEquals(expectedCelsius, actual, 0.01, "Temperature in Celsius should be correct");
    }

    @Test
    void testGetTemperatureInFahrenheit() {
        ThermistorHelper spyHelper = spy(thermistorHelper);
    // Mock Celsius conversion
        doReturn(25.0).when(spyHelper).getTemperatureInCelsius( channel );
        // expected answer is 25 * 9/5 + 32 = 77
        double fahrenheit = spyHelper.getTemperatureInFahrenheit( channel );
        assertEquals(77.0, fahrenheit, 0.01, "Fahrenheit should be Celsius * 9/5 + 32");
    }

    @Test
    public void testReadValueInvalidChannelLow() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> thermistorHelper.readADCValue(-1));
        assertEquals("Channel must be between 0 and 3", exception.getMessage());
    }

    @Test
    public void testReadValueInvalidChannelHigh() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> thermistorHelper.readADCValue(4));
        assertEquals("Channel must be between 0 and 3", exception.getMessage());
    }
}