package com.opensourcewithslu.inputdevices;
import com.pi4j.io.gpio.digital.DigitalInput;
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

    private DigitalInput clk;
    private DigitalInput dt;
    private DigitalInput sw;

    private int globalCounter;

    public RotaryEncoderHelper(@Named("sw") DigitalInput sw, @Named("dt") DigitalInput dt, @Named("clk") DigitalInput clk){
        this.sw = sw;
        this.dt = dt;
        this.clk = clk;
    }

    @PostConstruct
    public void initialize(){
        log.info("Initializing Rotary Encoder");

        clk.addListener(e -> {
            if(clk.equals(dt.state())){
                globalCounter++;
            }
            else if (!clk.equals(dt.state())){
                globalCounter--;
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
