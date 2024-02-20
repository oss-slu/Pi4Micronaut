package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.digital.DigitalOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The BuzzerHelper class contains methods that pertain to the control of the active and passive buzzers.
 */
public class BuzzerHelper {

    private static final Logger log = LoggerFactory.getLogger(BuzzerHelper.class);

    private final DigitalOutput buzzerOutput;

    private boolean buzzerState; //Determines the state of the buzzer

    private int passiveBuzzerFrequency = 440 ; //440Hz is commonly used standard pitch frequency

    /**
     * BuzzerHelper constructor
     * @param buzzerOutput An instance of a Pi4j DigitalOutput object
     */
    //tag::const[]
    public BuzzerHelper( DigitalOutput buzzerOutput){
    //end::const[]

        this.buzzerOutput = buzzerOutput;
    }

    /**
     * Initializes the state of the buzzer to false. Checks to ensure the buzzer is not on to avoid unexpected behavior
     */
    //tag::method[]
    public void initialize(){
    //end::method[]
        log.info("Initializing buzzer.");

        if (buzzerState){
            turnOff();
            buzzerState = false;
        }

    }

    /**
     * Turns the buzzer passive buzzer on by setting the output to on.
     */
    //tag::method[]
    public void passiveBuzzerOn(){
    //end::method[]
        log.info("Buzzer is on.");
        if (!buzzerState){
            buzzerState = true;
            buzzerOutput.isOn(); //Unsure if this should be high or on?
        }
    }

    /**
     * Turns the passive buzzer off by setting the output to off.
     */
    //tag::method[]
    public void passiveBuzzerOff(){
    //end::method[]
        log.info("Buzzer is off.");

        if (buzzerState){
            buzzerState = false;
            buzzerOutput.isOff();
        }

    }
    /**
     * Turns the active buzzer on by setting the output to low.
     */
    //tag::method[]
    public void activeBuzzerOn(){
    //end::method[]
        log.info("Active buzzer is on.");

        if (!buzzerState){
            buzzerState = true;
            buzzerOutput.isLow();
        }
    }
    /**
     * Turns the active buzzer off by setting the output to high.
     */
    //tag::method[]
    public void activeBuzzerOff(){
    //end::method[]
        log.info("Active Buzzer is off.");

        if (buzzerState){
            buzzerState = false;
            buzzerOutput.isHigh();
        }
    }

    /**
     *
     * @param frequency Alters the frequency state of the passive buzzer (which is initialized to the standard 440Hz)
     */
    //tag::method[]
    public void setFrequency(int frequency){
    //end::method[]
        if (frequency >= 20 && frequency <= 20000){ //Check to ensure the frequency is within human audible range
            passiveBuzzerFrequency = frequency;
        } else {
            log.error("Frequency is out of range. Please choose a value between 20 Hz and 20 kHz");
        }
    }

}
