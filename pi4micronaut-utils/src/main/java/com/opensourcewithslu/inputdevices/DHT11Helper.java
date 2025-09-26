package com.opensourcewithslu.inputdevices;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DHT11Helper {

    private static final int maxDelayCount = 100;
    private static final int bitOneDelayCount = 10;
    private static final int bitsLength = 40;
    private static final Logger log = LoggerFactory.getLogger(DHT11Helper.class);

    private final Context pi4j;
    private final DigitalInput dataInput;
    private final DigitalOutput dataOutput;

    public DHT11Helper(DigitalInput dataInput, DigitalOutput dataOutput) {
        this.pi4j = Pi4J.newAutoContext();
        this.dataInput = dataInput;
        this.dataOutput = dataOutput;
    }

    public void readData() throws Exception {
        int bitCount = 0;
        int delayCount = 0;
        StringBuilder bits = new StringBuilder();

        // -------- send start (output, then release) --------
        
        dataOutput.low();
        Thread.sleep(20); // 20ms start signal
        dataOutput.shutdown(pi4j);   // release pin


        // -------- wait for sensor response --------
        while (dataInput.isHigh()) {
            log.info("Waiting for DHT11 response...");
        }
        
        // -------- read 40 bits --------
        log.info("DHT11 response started. Counting BITS...");
        while (bitCount < bitsLength) {
            // wait for low-to-high

            // measure how long the signal stays HIGH
            delayCount = 0;
            while (dataInput.isHigh()) {
                delayCount++;
                if (delayCount > maxDelayCount) break;
            }

            if (delayCount > bitOneDelayCount) {
                bits.append("1");
            } else {
                bits.append("0");
            }

            bitCount++;
        }

        log.info("DHT11 bits read: {}", bits.toString());

        dataInput.shutdown(pi4j);

        // -------- parse values --------
        int humidityInt = Integer.parseInt(bits.substring(0, 8), 2);
        int humidityDec = Integer.parseInt(bits.substring(8, 16), 2);
        int tempInt = Integer.parseInt(bits.substring(16, 24), 2);
        int tempDec = Integer.parseInt(bits.substring(24, 32), 2);
        int checkSum = Integer.parseInt(bits.substring(32, 40), 2);

        int sum = humidityInt + humidityDec + tempInt + tempDec;

        double humidity, temperature;
        if (checkSum != sum) {
            humidity = 0.0;
            temperature = 0.0;
        } else {
            humidity = humidityInt + humidityDec / 10.0;
            temperature = tempInt + tempDec / 10.0;
        }

        log.info("DHT11 Reading - Temperature: {} C, Humidity: {} %", temperature, humidity);
    }

}