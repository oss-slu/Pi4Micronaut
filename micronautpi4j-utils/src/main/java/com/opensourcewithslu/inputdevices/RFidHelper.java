package com.opensourcewithslu.inputdevices;

import com.pi4j.crowpi.components.exceptions.RfidException;
import com.pi4j.context.Context;
import com.pi4j.io.spi.SpiConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pi4j.crowpi.components.RfidComponent;
import java.util.concurrent.atomic.AtomicReference;

public class RFidHelper {
    private static final Logger log = LoggerFactory.getLogger(RFidHelper.class);

    private final RfidComponent scanner;

    //tag::const[]
    public RFidHelper(SpiConfig config, int reset, Context pi4jContext)
    //end::const[]
    {
        this.scanner = new RfidComponent(pi4jContext, reset, config.getAddress(), config.getBaud());
    }
    
    //tag::const[]
    public RFidHelper(SpiConfig config, Context pi4jContext)
    //end::const[]
    {
        this.scanner = new RfidComponent(pi4jContext, config.getAddress(), config.getBaud());
    }

    //tag::method[]
    public void writeToCard(Object data)
    //end::method[]
    {
        scanner.waitForAnyCard(card -> {
            try {
                card.writeObject(data);
            } catch (RfidException e){
                log.info(e.getMessage());
            }
        });
    }

    //tag::method[]
    public Object readFromCard()
    //end::method[]
    {
        AtomicReference<Object> data = new AtomicReference<>(new Object());
        scanner.waitForAnyCard(card -> {
            try {
                data.set(card.readObject());
            } catch (RfidException e){
                log.info(e.getMessage());
            }
        });
        return data;
    }

    //tag::method[]
    public void resetScanner()
    //end::method[]
    {
        scanner.reset();
    }
}
