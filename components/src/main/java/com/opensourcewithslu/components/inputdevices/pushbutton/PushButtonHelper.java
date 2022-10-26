package com.opensourcewithslu.components.inputdevices.pushbutton;

import com.opensourcewithslu.components.outputdevices.led.LEDHelper;
import com.pi4j.io.gpio.digital.DigitalInput;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class PushButtonHelper {
    private static final Logger log = LoggerFactory.getLogger(PushButtonHelper.class);

    private DigitalInput buttonInput;

    private final LEDHelper ledHelper;

    public PushButtonHelper(LEDHelper ledHelper,
                            @Named("button-input") DigitalInput buttonInput){
        this.ledHelper = ledHelper;
        this.buttonInput = buttonInput;
    }

    @PostConstruct
    public void initializePushButton(){
        log.info("Initializing Push Button");

        buttonInput.addListener(e ->{
            if(buttonInput.isHigh()){
                System.out.println("PushButtonHelper, Led test, Led test");
                ledHelper.ledOn();
            }else {
                ledHelper.ledOff();
            }
        });
    }
}
