package com.opensourcewithslu.inputdevices;
import com.opensourcewithslu.utilities.MultiPinConfigs.DigitalInputMultiPinConfiguration;
import com.opensourcewithslu.utilities.MultipinConfiguration;
import com.opensourcewithslu.utilities.Pi4JMultipinFactory;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalListener;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Prototype;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Prototype
public class RotaryEncoderHelper {
    private static final Logger log = LoggerFactory.getLogger(RotaryEncoderHelper.class);
    private DigitalInput clk;
    private DigitalInput dt;
    private DigitalInput sw;
    private String helperName;
    private int globalCounter;

    public RotaryEncoderHelper(MultipinConfiguration multiPin){
        DigitalInput[] allPins = (DigitalInput[]) multiPin.getComponents();
        helperName = multiPin.getId().substring(0, multiPin.getId().length() - 8);
        this.sw = allPins[0];
        this.clk = allPins[1];
        this.dt = allPins[2];

        initialize();
    }

    public void initialize(){
        log.info("Initializing " + helperName);

        String logInfo = helperName + " counter is {}";

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
            log.info(logInfo, globalCounter);
        });

        sw.addListener(e -> {
            if(sw.isHigh()) {
                log.info("Resetting " + helperName + " counter");
                globalCounter = 0;
            }

            log.info(logInfo, globalCounter);
        });
    }

    public int getEncoderValue(){return this.globalCounter;}
}
