package com.opensourcewithslu.inputdevices;
import com.pi4j.Pi4J;
import com.pi4j.crowpi.components.exceptions.RfidException;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import com.pi4j.context.Context;
import com.pi4j.io.spi.SpiConfig;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pi4j.crowpi.components.RfidComponent;
import com.pi4j.crowpi.components.internal.rfid.RfidCard;
import com.pi4j.crowpi.components.events.EventHandler;


@Singleton
public class RFidHelper extends InputDevice {
    private static final Logger log = LoggerFactory.getLogger(RFidHelper.class);

    private RfidComponent scanner;
    private Context context;
    private EventHandler<RfidCard> handler;

    public RFidHelper(SpiConfig config, int reset){
        this.context = Pi4J.newAutoContext();
        this.scanner = new RfidComponent(this.context, reset, config.getAddress(), config.getBaud());
    }
    public RFidHelper(SpiConfig config){
        this.context = Pi4J.newAutoContext();
        this.scanner = new RfidComponent(this.context, config.getAddress(), config.getBaud());
    }

    @PostConstruct
    public void initialize(){
        log.info("starting Rfid scanner");
        scanner.waitForAnyCard(card -> {
            log.info("We did it. Hooray.");
        });
    }

    public void addEventListener(DigitalStateChangeListener function)  {

    }

    public void removeEventListener(DigitalStateChangeListener function){

    }
}
