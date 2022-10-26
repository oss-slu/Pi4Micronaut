package com.opensourcewithslu.components.outputdevices.led;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.context.annotation.Property;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class LightSensorHelper {
    private static final Logger log = LoggerFactory.getLogger(LightSensorHelper.class);

    private DigitalOutput sensorOutput;
    private DigitalInput sensorInput;


    private long startTime;
    private long endTime;

    private int darknessThreshold;
    private int darknessValue;


    public LightSensorHelper(
                             @Named("photo-resistor-output") DigitalOutput sensorOutput,
                             @Named("photo-resistor-input") DigitalInput sensorInput) {
        this.sensorOutput = sensorOutput;
        this.sensorInput = sensorInput;

        initializePhotoSensor();
    }

    private void initializePhotoSensor() {
        log.info("Initializing PhotoResistor");

        sensorInput.addListener(e -> {
            log.trace("input state change: {}", sensorInput.state().toString());
            if (sensorInput.isHigh()) {
                endTime = System.currentTimeMillis();
                darknessValue = (int) (endTime - startTime);
                log.debug("Darkness Level {}", darknessValue);
                updateLed(darknessValue > darknessThreshold);
            } else {
                startTime = System.currentTimeMillis();
                sensorOutput.high();
            }
        });
    }

    private void updateLed(boolean illuminate) {
        if (illuminate) {

        } else {

        }
    }

    @Scheduled(fixedRate = "5s")
    public void updateLED() {
        if (sensorInput.isHigh()) {
            sensorOutput.low();
        }
    }


    @Inject
    public void setDarknessThreshold(@Property(name = "led.darkness.threshold", defaultValue = "140") int darknessThreshold) {
        this.darknessThreshold = darknessThreshold;
        log.debug("Darkness Threshold Is {}", darknessThreshold);
    }

    public Darkness getDarkness() {
        return new Darkness(darknessValue);
    }
}
