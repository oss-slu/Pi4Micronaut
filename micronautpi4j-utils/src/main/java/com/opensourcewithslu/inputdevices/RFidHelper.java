package com.opensourcewithslu.inputdevices;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import io.micronaut.context.annotation.Context;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pi4j.cowpi.components.internal.rfid.MFRC522;
import com.pi4j.io.spi.Spi;

@Singleton
public class RFidHelper extends InputDevice {
    private static final Logger log = LoggerFactory.getLogger(RFidHelper.class);

    private DigitalOutput reset;
    private Spi spi;
    private MFRC522 scanner;

    public RFidHelper(@Named("reset") DigitalOutput reset, @Named("spi") Spi spi){
        this.reset = reset;
        this.spi = spi;
    }

    @PostConstruct
    public void initialize(){
        this.scanner = MFRC522(reset, spi);
    }

    public void addEventListener(DigitalStateChangeListener function)  {

    }

    public void removeEventListener(DigitalStateChangeListener function){

    }
}
