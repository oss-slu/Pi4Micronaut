package com.opensourcewithslu.inputdevices;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import io.micronaut.context.annotation.Prototype;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlideSwitchHelper {

    private static final Logger log = LoggerFactory.getLogger(SlideSwitchHelper.class);

    private DigitalInput slideSwitchInput;

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
        log.trace("Initializing Slide Switch");

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
