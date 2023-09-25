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

public class RotaryEncoderHelper {
    private static final Logger log = LoggerFactory.getLogger(RotaryEncoderHelper.class);
    private DigitalInput clk;
    private DigitalInput dt;
    private DigitalInput sw;
    private String helperName;
    private int globalCounter;

    //tag::const[]
    public RotaryEncoderHelper(MultipinConfiguration multiPin)
    //end::const[]
    {
        DigitalInput[] allPins = (DigitalInput[]) multiPin.getComponents();
        helperName = multiPin.getId().substring(0, multiPin.getId().length() - 8);
        this.sw = allPins[0];
        this.clk = allPins[1];
        this.dt = allPins[2];

        initialize();
    }

    //tag::method[]
    public void initialize()
    //end::method[]
    {
        log.trace("Initializing " + helperName);

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
            log.trace(logInfo, globalCounter);
        });

        sw.addListener(e -> {
            if(sw.isHigh()) {
                log.debug("Resetting " + helperName + " counter");
                globalCounter = 0;
            }

            log.debug(logInfo, globalCounter);
        });
    }

    //tag::method[]
    public int getEncoderValue()
    //end::method[]
    {
        return this.globalCounter;
    }
}
