package com.opensourcewithslu.inputdevices;

import com.pi4j.io.spi.Spi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThermistorHelper {
    private static final Logger log = LoggerFactory.getLogger(ThermistorHelper.class);
    private final Spi spi; // SPI interface for ADC communication
    private final ADC0834ConverterHelper adcConverterHelper;

    // Constants for Steinhart-Hart equation (example values; replace with actual thermistor constants)
    private static final double A = 0.001129148;
    private static final double B = 0.000234125;
    private static final double C = 0.0000000876741;

    public ThermistorHelper( ADC0834ConverterHelper adcConverterHelper ) {
        this.adcConverterHelper = adcConverterHelper;
        this.spi = adcConverterHelper.getSpi();
        log.info("SPI and ADCConverter for thermistor initialized.");
    }

    /**
 * Gets the SPI interface used by this class.
 * this is used for ThermistorHelperTest to mock SPI interactions.
 * 
 * @return the SPI interface
 */
    Spi getSpi() {  
    return spi;
    }

    /**
     * Reads the raw value from the thermistor via ADC and converts it to resistance.
     * @return the resistance value of the thermistor
     */
    public double getResistance(int channel) {
        double rawValue = readADCValue(channel);
        return convertRawToResistance(rawValue);
    }

    /**
     * Reads the ADC value from the thermistor on ADC0834.
     * @return the raw ADC value as a double
     */
    public double readADCValue( int channel ) {
        return adcConverterHelper.readValue(channel);
    }


    /**
     * Converts the raw ADC value to thermistor resistance.
     * @param rawValue the raw ADC value.
     * @return the calculated resistance.
     */
    private double convertRawToResistance(double rawValue) {
        // Convert ADC reading to resistance; adjust formula based on thermistor and ADC scaling
        double voltage = (rawValue / 1023.0) * 3.3; // Assuming a 3.3V reference voltage
        double resistance = (10000 * (3.3 - voltage)) / voltage; // Example formula for voltage divider circuit
        log.info("Calculated Resistance: {} ohms", resistance);
        return resistance;
    }

    /**
     * Calculates the temperature in Celsius using the Steinhart-Hart equation.
     * @return temperature in Celsius.
     */
    public double getTemperatureInCelsius(int channel) {
        double resistance = getResistance(channel);
        double temperatureInKelvin = 1.0 / (A + B * Math.log(resistance) + C * Math.pow(Math.log(resistance), 3));
        double temperatureCelsius = temperatureInKelvin - 273.15;
        log.info("Temperature in Celsius: {}", temperatureCelsius);
        return temperatureCelsius;
    }

    /**
     * Converts temperature in Celsius to Fahrenheit.
     * @return temperature in Fahrenheit.
     */
    public double getTemperatureInFahrenheit(int channel) {
        double temperatureFahrenheit = getTemperatureInCelsius(channel) * 9 / 5 + 32;
        log.info("Temperature in Fahrenheit: {}", temperatureFahrenheit);
        return temperatureFahrenheit;
    }
}
