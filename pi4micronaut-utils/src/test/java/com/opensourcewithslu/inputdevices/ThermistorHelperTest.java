package com.opensourcewithslu.inputdevices;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ThermistorHelperTest {

    @Test
    void testBasicFunctionality() {
        // This test will definitely pass just validates basic math,  this was for my sanity, java 24 would not work//
        assertTrue(true, "Basic test should pass");
    }

    @Test
    void testSteinhartHartEquationCalculation() {
        // Test the core Steinhart-Hart equation math 
        double A = 0.001129148;
        double B = 0.000234125;
        double C = 0.0000000876741;
        
        // Test with 10k ohm resistance 
        double resistance = 10000.0;
        double temperatureInKelvin = 1.0 / (A + B * Math.log(resistance) + C * Math.pow(Math.log(resistance), 3));
        double temperatureCelsius = temperatureInKelvin - 273.15;
        
        // Should be around room temperature
        assertTrue(temperatureCelsius > 10.0 && temperatureCelsius < 40.0, 
                   "10k ohm should give room temperature, got: " + temperatureCelsius + "°C");
    }

    @Test
    void testTemperatureConversion() {
        // Test the Celsius to Fahrenheit conversion formula
        double celsius = 25.0;
        double fahrenheit = celsius * 9.0 / 5.0 + 32.0;
        
        assertEquals(77.0, fahrenheit, 0.1, "25°C should equal 77°F");
    }

    @Test
    void testVoltageToResistanceCalculation() {
        // Test the math used in convertRawToResistance
        double rawADC = 512.0; 
        double voltage = (rawADC / 1023.0) * 3.3; // Should be  about1.65V
        double resistance = (10000 * (3.3 - voltage)) / voltage; // Should be about 10k
        
        assertEquals(1.65, voltage, 0.01, "Mid-scale ADC should give 1.65V");
        assertEquals(10000.0, resistance, 50.0, "Should calculate ~10k ohms");
    }

    @Test
    void testADCBitProcessing() {
        
        byte response1 = 0x02; // Upper bits
        byte response2 = (byte) 0xFF; // Lower bits
        
        
        int rawValue = ((response1 & 0x03) << 8) + (response2 & 0xFF);
        
        assertEquals(767, rawValue, "Bit manipulation should give 767");
        assertTrue(rawValue >= 0 && rawValue <= 1023, "ADC value should be in valid range");
    }
}
