package com.opensourcewithslu.inputdevices;

import com.pi4j.crowpi.components.exceptions.RfidException;
import com.pi4j.context.Context;
import com.pi4j.io.spi.SpiConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pi4j.crowpi.components.RfidComponent;
import java.util.concurrent.atomic.AtomicReference;


/**
 *  The RFIDHelper class is for interacting with an RFID scanner.
 */
public class RFidHelper {
    private static final Logger log = LoggerFactory.getLogger(RFidHelper.class);

    private final RfidComponent scanner;

    /**
     * The RFidHelper constructor WITH the reset pin as a parameter.
     * @param config A Pi4J SPIConfig object which holds the SPI address and SPI Baud rate for RFID scanner.
     * @param reset Defines the reset pin for the RFID scanner.
     * @param pi4jContext The Pi4J context object.
     */
    //tag::const[]
    public RFidHelper(SpiConfig config, int reset, Context pi4jContext)
    //end::const[]
    {
        this.scanner = new RfidComponent(pi4jContext, reset, config.getAddress(), config.getBaud());
    }

    /**
     * The RFidHelper constructor WITHOUT the reset pin as a parameter.
     * @param config A Pi4J SPIConfig object which holds the SPI address and SPI Baud rate for RFID scanner.
     * @param pi4jContext The Pi4J context object.
     */
    //tag::const[]
    public RFidHelper(SpiConfig config, Context pi4jContext)
    //end::const[]
    {
        this.scanner = new RfidComponent(pi4jContext, config.getAddress(), config.getBaud());
    }

    /**
     * Writes data to an RFID fob. This is the data that is returned when the fob is scanned by the scanner.
     * @param data Data to be written to an RFID fob. Typically, a string identifying the holder of the fob such as an identification number.
     */
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

    /**
     *  When called, this method waits for any RFID card/fob to be scanned. The data from the card is returned.
     * @return The data read from the card/fob.
     */
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

    /**
     * Resets the RFID scanner.
     */
    //tag::method[]
    public void resetScanner()
    //end::method[]
    {
        scanner.reset();
    }
}
