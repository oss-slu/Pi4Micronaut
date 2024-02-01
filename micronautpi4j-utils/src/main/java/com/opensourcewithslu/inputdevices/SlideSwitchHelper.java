package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlideSwitchHelper {
    private static final Logger log = LoggerFactory.getLogger(SlideSwitchHelper.class);

    private final DigitalInput slideSwitchInput;

    public boolean isOn;

    //tag::const[]
    public SlideSwitchHelper(DigitalInput slideSwitchInput) 
    //end::const[]
    {
        this.slideSwitchInput = slideSwitchInput;
        this.isOn = slideSwitchInput.isHigh();

        initialize();
    }

    //tag::method[]
    public void initialize()
    //end::method[]
    {
        log.info("Initializing Slide Switch");

        slideSwitchInput.addListener(e->{
            isOn = slideSwitchInput.isHigh();
        });

    }

    //tag::method[]
    public void addEventListener(DigitalStateChangeListener function) 
    //end::method[]
    {
        slideSwitchInput.addListener(function);
    }

    //tag::method[]
    public void removeEventListener(DigitalStateChangeListener function) 
    //end::method[]
    {
        slideSwitchInput.removeListener(function);
    }
}
