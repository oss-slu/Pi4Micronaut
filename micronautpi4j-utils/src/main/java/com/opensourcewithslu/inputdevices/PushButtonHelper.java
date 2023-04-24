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

public class PushButtonHelper {
    private static final Logger log = LoggerFactory.getLogger(PushButtonHelper.class);

    private DigitalInput buttonInput;
    public Boolean isPressed;

    //tag::const[]
    public PushButtonHelper(DigitalInput buttonInput)
    //end::const[]
    {
        this.buttonInput = buttonInput;
        this.isPressed = buttonInput.isHigh();

        initialize();
    }

    //tag::method[]
    public void initialize()
    //end::method[]
    {
        log.info("Initializing " + buttonInput.getName());

        buttonInput.addListener(e->{
           isPressed = buttonInput.isHigh();
        });
    }

    //tag::method[]
    public void addEventListener(DigitalStateChangeListener function)  
    //end::method[]
    {
        buttonInput.addListener(function);
    }

    //tag::method[]
    public void removeEventListener(DigitalStateChangeListener function)
    //end::method[]
    {
        buttonInput.removeListener(function);
    }
}
