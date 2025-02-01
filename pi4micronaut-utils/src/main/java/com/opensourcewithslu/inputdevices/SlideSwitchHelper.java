package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The SlideSwitchHelper class is used to initialize a slide switch.
 */
public class SlideSwitchHelper {
    private static Logger log = LoggerFactory.getLogger(SlideSwitchHelper.class);

    private final DigitalInput slideSwitchInput;

    /**
     * Shows if the slide switch is on.
     */
    public boolean isOn;

    /**
     * SlideSwitchHelper constructor.
     * @param slideSwitchInput A Pi4J DigitalInput Object.
     */
    //tag::const[]
    public SlideSwitchHelper(DigitalInput slideSwitchInput) 
    //end::const[]
    {
        this.slideSwitchInput = slideSwitchInput;
        this.isOn = slideSwitchInput.isHigh();

        initialize();
    }

    /**
     * Initializes the listener that keeps track of whether the slide switch is high/low. Automatically called when the SlideSwitchHelper is instantiated.
     */
    //tag::method[]
    public void initialize()
    //end::method[]
    {
        log.info("Initializing Slide Switch");

        // Update the current state and log the appropriate message
        isOn = slideSwitchInput.isHigh();
        if (isOn) {
            log.info("Turning Switch On");
        } else {
            log.info("Turning Switch Off");
        }

        // Add a single listener that updates the state and logs on change
        slideSwitchInput.addListener(e-> {
            boolean currentState = slideSwitchInput.isHigh();
            if (currentState != isOn) {
                isOn = currentState;
                if (isOn) {
                    log.info("Turning Switch On");
                } else {
                    log.info("Turning Switch Off");
                }
            }
        });
    }

    /**
     * Adds an EvenListener to the slide switch.
     * @param function A Pi4J DigitalStateChangeListener object.
     */
    //tag::method[]
    public void addEventListener(DigitalStateChangeListener function) 
    //end::method[]
    {
        slideSwitchInput.addListener(function);
    }

    /**
     * Removes the EventListener from the slide switch.
     * @param function A Pi4J DigitalStateChangeListener object.
     */
    //tag::method[]
    public void removeEventListener(DigitalStateChangeListener function) 
    //end::method[]
    {
        slideSwitchInput.removeListener(function);
    }

    /**
     *
     * @param log Logger object to set the logger to.
     */
    public void setLog(Logger log) { this.log = log; }
}
