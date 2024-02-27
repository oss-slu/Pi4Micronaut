package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The PhotoResistorHelper class is used to work with the functionalities of a Photo Resistor.
 */
public class PhotoResistorHelper {

    private static final Logger log = LoggerFactory.getLogger(PhotoResistorHelper.class);
    private final DigitalInput photoResistorInput;
    private final DigitalOutput photoResistorOutput;
    private DigitalStateChangeListener photoResistorInputListener;
    private long startTime;
    private long endTime;
    private int darknessThreshold = 140; //default value
    private int darknessValue;
    /**
     * To Activate and Deactivate the Photo Resistor
     */
    private boolean sensorActive;
    /**
     * To check if it is Dark.
     */
    public boolean isDark;

    /**
     * TouchSwitchHelper constructor.
     * @param sensorInput - A Pi4J DigitalInput object.
     * @param sensorOutput - A Pi4J DigitalOutput object.
     */
    public PhotoResistorHelper(DigitalInput sensorInput, DigitalOutput sensorOutput){
        this.photoResistorInput = sensorInput;
        this.photoResistorOutput = sensorOutput;
        photoResistorOutput.high();
        this.isDark = photoResistorInput.isHigh();
    }
    /**
     * @return current darknessValue.
     */
    public int getDark() {
        return darknessValue;
    }
    /**
     * Initializes the Photo Resistor and calls the updateDarkness function for every half second.
     */
    public void initialize() {
        log.info("Photo Resistor Initialized");
        sensorActive = true;
        if (sensorActive) {
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            executorService.scheduleAtFixedRate(this::updateDark, 0, 500, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * Updates the darknessValue of the Photo Resistor.
     */
    public void updateDark()
    {
        setToLow();
        photoResistorInput.addListener( e -> {
            if (photoResistorInput.isHigh()) {
                endTime = System.currentTimeMillis();
                darknessValue = (int) (endTime - startTime);
                log.info("Darkness Level {}", darknessValue);
                isDark = darknessValue > darknessThreshold;

            } else {
                startTime = System.currentTimeMillis();
                this.photoResistorOutput.high();
                setToLow();
            }

        });

    }

    /**
     * To set the Photo Resistor output to low for each event.
     */
    public void setToLow() {
        if (photoResistorInput.isHigh()) {
            photoResistorOutput.low();
        }
    }

    /**
     * To set a threshold for Photo Resistor.
     * @param darknessThreshold value is obtained from user.
     */
    public void setDarknessThreshold(int darknessThreshold) {
        this.darknessThreshold = darknessThreshold;
        log.info("Darkness Threshold Is {}", darknessThreshold);
    }

    /**
     * Adds an event listener to the Photo Resistor.
     * @param function A Pi4J DigitalStateChangeListener object.
     */
    public void addEventListener(DigitalStateChangeListener function)
    {
        photoResistorInputListener = function;
        photoResistorInput.addListener(photoResistorInputListener);
    }

    /**
     * Removes the event listener from the Photo Resistor.
     */
    public void removeEventListener()
    {
        sensorActive=false;
        if (photoResistorInputListener != null) {
            photoResistorInput.removeListener(photoResistorInputListener);
            photoResistorInputListener = null;
        }
    }

}