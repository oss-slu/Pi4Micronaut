package com.opensourcewithslu.inputdevices;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class SlideSwitchHelper {

    private static final Logger log = LoggerFactory.getLogger(SlideSwitchHelper.class);

    private DigitalInput slideSwitchInput;

    public boolean isOn;

    public SlideSwitchHelper(@Named("slide-switch-input") DigitalInput slideSwitchInput) {
        this.slideSwitchInput = slideSwitchInput;
        this.isOn = slideSwitchInput.isHigh();
    }

    @PostConstruct
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
