package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThermistorHelper {
    private static final Logger log = LoggerFactory.getLogger(ThermistorHelper.class);
    private DigitalInput sensorInput;
    private DigitalOutput sensorOutput;

    public ThermistorHelper(DigitalInput sensorInput, DigitalOutput sensorOutput) {
        this.sensorInput = sensorInput;
        this.sensorOutput = sensorOutput;
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
        double tempCelsius = 1.0 / (0.001129148 + (0.000234125 * Math.log(resistance)) + (0.0000000876741 * Math.pow(Math.log(resistance), 3))) - 273.15;
        return tempCelsius;
    }

    private double calculateResistance(int rawValue) {
        double Vout = (rawValue / 1023.0) * 3.3;
        return (10_000 * Vout) / (3.3 - Vout);
    }

    private int getThermistorValue() {
        return 1;
    }
}

