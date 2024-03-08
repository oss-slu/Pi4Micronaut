package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.*;

public class UltraSonicSensorHelper {

    private static final Logger log = LoggerFactory.getLogger(UltraSonicSensorHelper.class);
    private final DigitalOutput triggerPin; // This is the Trigger Pin - we send the pulse
    private final DigitalInput echoPin;     // This is the Echo Pin - we read the return signal
    private volatile boolean sensorActive;
    private volatile double distance;
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public UltraSonicSensorHelper(DigitalOutput triggerPin, DigitalInput echoPin) {
        this.triggerPin = triggerPin;
        this.echoPin = echoPin;
        initialize();
    }

    public void initialize() {
        log.info("Ultrasonic Sensor Initialized");
        sensorActive = true;
        // TriggerPin is low initially
        triggerPin.low();
    }

    public void startMeasuring() {
        if (!sensorActive) {
            return;
        }
        executorService.scheduleAtFixedRate(this::triggerAndMeasureDistance, 0, 100, TimeUnit.MILLISECONDS);
    }

    private void triggerAndMeasureDistance() {
        try {
            // Trigger pin is low for a short period before sending the pulse
            triggerPin.low();
            TimeUnit.MICROSECONDS.sleep(2);

            // Sending a 10-microsecond pulse on the trigger pin
            triggerPin.high();
            TimeUnit.MICROSECONDS.sleep(10);
            triggerPin.low();

            // Waiting for the echo pin to go high, signaling the start of the pulse return
            long start = System.nanoTime();
            while (echoPin.isLow()) {
                if ((System.nanoTime() - start) / 1000 > 20000) { // 20 ms timeout
                    log.debug("Timeout waiting for echo signal start");
                    distance=0;
                    return;
                }
            }
            long startTime = System.nanoTime();

            // Waiting for the echo pin to go low, signaling the end of the pulse return
            while (echoPin.isHigh()) {
                if ((System.nanoTime() - startTime) / 1000 > 30000) { // 30 ms timeout
                    log.debug("Timeout waiting for echo signal end");
                    distance = 0;
                    return;
                }
            }
            long endTime = System.nanoTime();

            // Calculate the distance
            calculateDistance(endTime - startTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Ultrasonic sensor measurement interrupted", e);
            distance = 0;
        }
    }

    private void calculateDistance(long durationInNano) {
        // Converting nanoseconds to microseconds for distance calculation
        double durationInMicroseconds = TimeUnit.NANOSECONDS.toMicros(durationInNano);
        // Calculate distance in centimeters
        distance = (durationInMicroseconds / 58.0);
        log.info("Distance measured: {} cm", distance);
    }

    public double getDistanceInCentimeter() {
        return distance;
    }

    public double getDistanceInMeters() {
        return distance/100;
    }

    public void stopMeasuring() {
        sensorActive = false;
        if (!executorService.isShutdown()) {
            executorService.shutdownNow();
        }
    }
}