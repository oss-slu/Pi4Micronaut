package com.opensourcewithslu.inputdevices;

import com.pi4j.context.Context;
import com.pi4j.io.spi.Spi;
import com.pi4j.io.spi.SpiConfig;
import com.pi4j.io.spi.SpiProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pi4j.io.gpio.digital.DigitalInput;

public class ThermistorHelper {
    private static final Logger log = LoggerFactory.getLogger(ThermistorHelper.class);
    private final DigitalInput sensorInput;
    private Spi spiDevice;

    public ThermistorHelper(Context pi4j, DigitalInput sensorInput) {
        this.sensorInput = sensorInput;
        initializeSpi(pi4j);
    }

    private void initializeSpi(Context pi4j) {
        // Obtain SPI provider from Pi4J context
        SpiProvider spiProvider = pi4j.provider("pigpio-spi");
        SpiConfig spiConfig = Spi.newConfigBuilder(pi4j)
                .id("spi-adc0834")
                .name("ADC0834 SPI Device")
                .address(0)  // SPI Channel 0
                .baud(100_000) // Set appropriate baud rate
                .build();

        // Initialize SPI device
        this.spiDevice = spiProvider.create(spiConfig);
    }

    public double getTemperatureCelsius() {
        int rawValue = getThermistorValue();
        return convertRawToCelsius(rawValue);
    }

    public double getTemperatureFahrenheit() {
        double celsius = getTemperatureCelsius();
        return celsius * 9.0 / 5.0 + 32;
    }

    private double convertRawToCelsius(int rawValue) {
        double resistance = calculateResistance(rawValue);
        return 1.0 / (0.001129148 + (0.000234125 * Math.log(resistance)) + (0.0000000876741 * Math.pow(Math.log(resistance), 3))) - 273.15;
    }

    private double calculateResistance(int rawValue) {
        double Vout = (rawValue / 255.0) * 3.3; // 8-bit value with 3.3V reference
        return (10_000 * Vout) / (3.3 - Vout);
    }

    private int getThermistorValue() {
        byte[] request = {(byte) 0b11010000, 0};  // Start command for ADC0834

        // Send the request
        spiDevice.write(request);

        // Prepare a buffer for the response
        byte[] response = new byte[2];

        // Read the response (if your SPI device allows a separate read)
        spiDevice.read(response, 0, 2);

        // Extract the ADC value from the response
        int rawValue = response[1] & 0xFF;
        log.info("Raw thermistor value from ADC0834: " + rawValue);
        return rawValue;
    }
}
