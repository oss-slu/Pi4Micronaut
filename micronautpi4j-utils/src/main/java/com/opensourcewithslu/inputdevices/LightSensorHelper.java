package com.opensourcewithslu.inputdevices;


import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.context.annotation.Property;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.opensourcewithslu.outputdevices.LEDHelper;

@Singleton
public class LightSensorHelper {

    private static final Logger log = LoggerFactory.getLogger(LightSensorHelper.class);

    private DigitalInput sensorInput;
    private DigitalOutput sensorOutput;

    private final LEDHelper ledHelper;

    private long startTime;
    private long endTime;

    private int darknessThreshold;
    private int darknessValue;


    public LightSensorHelper(LEDHelper ledHelper,
                             @Named("photo-resistor-input") DigitalInput sensorInput,
                             @Named("photo-resistor-output") DigitalOutput sensorOutput) {
        this.ledHelper = ledHelper;
        this.sensorInput = sensorInput;
        this.sensorOutput = sensorOutput;

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
            ledHelper.deviceOn();
        }
        else {
            ledHelper.deviceOff();
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
