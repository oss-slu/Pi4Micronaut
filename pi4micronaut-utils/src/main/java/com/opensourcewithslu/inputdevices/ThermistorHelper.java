package com.opensourcewithslu.inputdevices;

import com.pi4j.context.Context;
import com.pi4j.io.spi.Spi;
import com.pi4j.io.spi.SpiConfig;
import com.pi4j.io.spi.SpiMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThermistorHelper {
    private static final Logger log = LoggerFactory.getLogger(ThermistorHelper.class);
    private final Spi spi; // SPI interface for ADC communication

    // Constants for Steinhart-Hart equation (example values; replace with actual thermistor constants)
    private static final double A = 0.001129148;
    private static final double B = 0.000234125;
    private static final double C = 0.0000000876741;

    public ThermistorHelper(Context pi4j) {
        this.spi = initializeADC(pi4j);
    }

    /**
     * Initialize SPI and ADC settings specific to ADC0834.
     */
    private Spi initializeADC(Context pi4j) {
        try {
            // Create SPI configuration for the ADC0834
            SpiConfig spiConfig = Spi.newConfigBuilder(pi4j)
                    .id("ADC0834")
                    .name("Thermistor ADC")
                    .address(17) // SPI channel 0
                    .baud(1000000) // 1 MHz SPI clock speed
                    .mode(SpiMode.MODE_0)
                    .build();

            Spi spi = pi4j.create(spiConfig);
            log.info("SPI and ADC for thermistor initialized.");
            return spi;

        } catch (Exception e) {
            log.error("Failed to initialize SPI for ADC0834", e);
            throw new RuntimeException("SPI initialization failed", e);
        }
    }

    /**
     * Reads the raw value from the thermistor via ADC and converts it to resistance.
     * @return the resistance value of the thermistor
     */
    public double getResistance() {
        double rawValue = readADCValue();
        return convertRawToResistance(rawValue);
    }

    /**
     * Reads the ADC value from the thermistor on ADC0834.
     * @return the raw ADC value as a double
     */
    public double readADCValue() {
        byte[] packet = new byte[3];
        byte[] response = new byte[3]; // To store the response from ADC0834

        // ADC0834 requires sending a "start" bit sequence to initiate the read
        packet[0] = 0x01; // Start bit
        packet[1] = (byte) (0x80); // Single-ended mode and channel selection (channel 0 for thermistor)
        packet[2] = 0x00; // Placeholder byte to read data

        try {
            // Send the command packet and receive response from ADC0834
            spi.write(packet); // Send the packet
            spi.read(response); // Read the response into the response array

            // Process the response to extract ADC value (10-bit resolution for ADC0834)
            int rawValue = ((response[1] & 0x03) << 8) + (response[2] & 0xFF); // Combine bits for 10-bit ADC result
            log.info("Raw ADC Value: {}", rawValue);
            return rawValue;
        } catch (Exception e) {
            log.error("Failed to read from ADC0834", e);
            return -1; // Return a flag value indicating an error
        }
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
    public double getTemperatureInCelsius() {
        double resistance = getResistance();
        double temperatureInKelvin = 1.0 / (A + B * Math.log(resistance) + C * Math.pow(Math.log(resistance), 3));
        double temperatureCelsius = temperatureInKelvin - 273.15;
        log.info("Temperature in Celsius: {}", temperatureCelsius);
        return temperatureCelsius;
    }

    /**
     * Converts temperature in Celsius to Fahrenheit.
     * @return temperature in Fahrenheit.
     */
    public double getTemperatureInFahrenheit() {
        double temperatureFahrenheit = getTemperatureInCelsius() * 9 / 5 + 32;
        log.info("Temperature in Fahrenheit: {}", temperatureFahrenheit);
        return temperatureFahrenheit;
    }
}
