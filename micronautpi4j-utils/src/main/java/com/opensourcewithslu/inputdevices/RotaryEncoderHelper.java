package com.opensourcewithslu.inputdevices;
import com.opensourcewithslu.utilities.MultiPinConfigs.DigitalInputMultiPinConfiguration;
import com.opensourcewithslu.utilities.MultipinConfiguration;
import com.opensourcewithslu.utilities.Pi4JMultipinFactory;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalListener;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import io.micronaut.context.annotation.Context;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class RotaryEncoderHelper extends InputDevice {
    private static final Logger log = LoggerFactory.getLogger(RotaryEncoderHelper.class);

    private final MultipinConfiguration multiPin;
    private DigitalInput clk;
    private DigitalInput dt;
    private DigitalInput sw;

    private int globalCounter;

    public RotaryEncoderHelper(@Named("rotary-encoder") MultipinConfiguration multiPin){
        this.multiPin = multiPin;
    }

    @PostConstruct
    public void initialize(){
        log.info("Initializing Rotary Encoder");

        DigitalInput[] allPins = (DigitalInput[]) this.multiPin.getComponents();

        log.info("Setting pins");
        this.sw = allPins[0];
        this.dt = allPins[1];
        this.clk = allPins[2];

        clk.addListener(e -> {
            if(clk.equals(dt.state())){
                if(globalCounter == 2147483647){
                    globalCounter = -2147483647;
                }
                else {
                    globalCounter++;
                }
            }
            else if (!clk.equals(dt.state())){
                if(globalCounter == -2147483648){
                    globalCounter = 2147483646;
                }
                else{
                    globalCounter--;
                }
            }
            log.info("Global Counter is {}", globalCounter);
        });

        sw.addListener(e -> {
            if(sw.isHigh()) {
                log.info("Resetting globalCounter");
                globalCounter = 0;
            }

            log.info("Global Counter is {}", globalCounter);
        });
    }

    public int getEncoderValue(){return this.globalCounter;}

    public void addEventListener(DigitalStateChangeListener function)  {

    }

    public void removeEventListener(DigitalStateChangeListener function){

    }
}
