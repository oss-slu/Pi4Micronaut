package com.opensourcewithslu.outputdevices;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Prototype;
import io.micronaut.inject.qualifiers.Qualifiers;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Prototype
public class LEDHelper extends OutputDevice {
    private static final Logger log = LoggerFactory.getLogger(LEDHelper.class);

    private DigitalOutput ledOutput;
    private final ApplicationContext appContext;

    public LEDHelper(ApplicationContext appContext){
        this.appContext = appContext;
    }

//    @Inject
//    ApplicationContext appContext;
//
//    public LEDHelper(@Named("led") DigitalOutput ledOutput, ApplicationContext appContext) {
//        this.ledOutput = ledOutput;
//    }
//
//    public LEDHelper(String name){
//        DigitalOutput ledHold = appContext.getBean(DigitalOutput.class, Qualifiers.byName(name));
//        this.ledOutput = ledHold;
//    }
//
//    public LEDHelper(String name, ApplicationContext appContext){
//        DigitalOutput ledHold = appContext.getBean(DigitalOutput.class, Qualifiers.byName(name));
//
//        this.ledOutput = ledHold;
//    }

    public void setBean(String name){
        this.ledOutput = appContext.getBean(DigitalOutput.class, Qualifiers.byName(name));
    }

    public void deviceOn() {
        if (ledOutput.isLow()) {
            log.info("Turning off LED");
            ledOutput.high();
        }
    }

    public void deviceOff() {
        if (ledOutput.isHigh()) {
            log.info("Turning on LED");
            ledOutput.low();
        }
    }

    public void switchState(){
        if(ledOutput.isHigh()){
            deviceOff();
        }
        else{
            deviceOn();
        }
    }
}
