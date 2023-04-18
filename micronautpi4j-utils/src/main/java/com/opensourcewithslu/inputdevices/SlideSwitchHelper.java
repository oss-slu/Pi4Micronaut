package com.opensourcewithslu.inputdevices;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import io.micronaut.context.annotation.Prototype;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Prototype
public class SlideSwitchHelper {

    private static final Logger log = LoggerFactory.getLogger(SlideSwitchHelper.class);

    private DigitalInput slideSwitchInput;

    public boolean isOn;

    public SlideSwitchHelper(DigitalInput slideSwitchInput) {
        this.slideSwitchInput = slideSwitchInput;
        this.isOn = slideSwitchInput.isHigh();

        initialize();
    }

    public void initialize(){
        log.info("Initializing Slide Switch");

        slideSwitchInput.addListener(e->{
            isOn = slideSwitchInput.isHigh();
        });

    }


    public void addEventListener(DigitalStateChangeListener function) {
        slideSwitchInput.addListener(function);
    }


    public void removeEventListener(DigitalStateChangeListener function) {
        slideSwitchInput.removeListener(function);
    }
}
