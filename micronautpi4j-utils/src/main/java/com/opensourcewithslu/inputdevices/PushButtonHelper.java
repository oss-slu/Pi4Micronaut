package com.opensourcewithslu.inputdevices;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class PushButtonHelper {
    private static final Logger log = LoggerFactory.getLogger(PushButtonHelper.class);

    private DigitalInput buttonInput;
    public Boolean isPressed;

    public PushButtonHelper(@Named("button-input") DigitalInput buttonInput){
        this.buttonInput = buttonInput;
        this.isPressed = buttonInput.isHigh();
    }

    @PostConstruct
    public void initialize(){
        log.info("Initializing Push Button");

        buttonInput.addListener(e->{
           isPressed = buttonInput.isHigh();
        });
    }

    public void addEventListener(DigitalStateChangeListener function)  {
        buttonInput.addListener(function);
    }

    public void removeEventListener(DigitalStateChangeListener function){
        buttonInput.removeListener(function);
    }
}
