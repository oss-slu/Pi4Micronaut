package com.opensourcewithslu.outputdevices;

import com.pi4j.io.gpio.Gpio;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.pwm.Pwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The BuzzerHelper class contains methods that pertain to the control of the active and passive buzzers.
 */
public class BuzzerHelper {

    private static final Logger log = LoggerFactory.getLogger(BuzzerHelper.class);

    private final DigitalOutput activebuzzerOutput;

    //private final Pwm passiveBuzzerOutput;

    private boolean buzzerState; //Determines the state of the buzzer

    //private int passiveBuzzerFrequency = 440 ; //440Hz is commonly used standard pitch frequency

    /**
     * BuzzerHelper constructor
     * @param activebuzzerOutput An instance of a Pi4j DigitalOutput object
     */
    //tag::const[]
    public BuzzerHelper( DigitalOutput activebuzzerOutput){
    //end::const[]

        this.activebuzzerOutput = activebuzzerOutput;
       // this.passiveBuzzerOutput = passiveBuzzerOutput;
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
            activebuzzerOutput.low();
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
            activebuzzerOutput.high();
        }
    }

    public void constantTone(){
        while (true){
            log.info("Buzzer on.");
            activeBuzzerOn();
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
            log.info("Buzzer off.");
            activeBuzzerOff();
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }





    /**
     * The following functions must go into a separate passive buzzer helper.
     * -----------------------------------------------------------------------
     *
     *
     *
     *      * Initializes the state of the buzzer to false. Checks to ensure the buzzer is not on to avoid unexpected behavior
     *
     *     //tag::method[]
     *     public void initialize(){
     *     //end::method[]
     *         log.info("Initializing buzzer.");
     *
     *         if (buzzerState){
     *             buzzerState = false;
     *         }
     *
     *     }
     *
     *
      *      * Turns the buzzer passive buzzer on by setting the output to on.
      *
      *     //tag::method[]
      *     public void passiveBuzzerOn(){
      *     //end::method[]
      *         log.info("Buzzer is on.");
      *         if (!buzzerState){
      *             buzzerState = true;
      *             buzzerOutput.low();
      *         }
      *     }
      *
      *
      *      * Turns the passive buzzer off by setting the output to off.
      *
      *     //tag::method[]
      *     public void passiveBuzzerOff(){
      *     //end::method[]
      *         log.info("Buzzer is off.");
      *
      *         if (buzzerState){
      *             buzzerState = false;
      *             buzzerOutput.high();
      *         }
      *
      *     }
      *
     *
     *
     *      //tag::method[]
     *     public void setFrequency(int frequency){
     *     //end::method[]
     *         if (frequency >= 20 && frequency <= 20000){ //Check to ensure the frequency is within human audible range
     *             passiveBuzzerFrequency = frequency;
     *         } else {
     *             log.error("Frequency is out of range. Please choose a value between 20 Hz and 20 kHz");
     *         }
     *     }
     *
     *      *
     *      * @return Frequency: returns the int value of the frequency for the passive buzzer.
     *
     *
     *     //tag::method[]
     *     public int getFrequency(){
     *     //end::method[]
     *         return passiveBuzzerFrequency;
     *     }
     *
     **/

}
