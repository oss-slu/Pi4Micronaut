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
public class PushButtonHelper extends InputDevice {
    private static final Logger log = LoggerFactory.getLogger(PushButtonHelper.class);

    private DigitalInput buttonInput;
    public Boolean isPressed;

    public PushButtonHelper(DigitalInput buttonInput){
        this.buttonInput = buttonInput;
        this.isPressed = buttonInput.isHigh();

        initialize();
    }

    public void initialize(){
        log.info("Initializing " + buttonInput.getName());

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
