package com.opensourcewithslu.outputdevices;

import com.pi4j.io.pwm.Pwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * The PassiveBuzzerHelper class contains methods that pertain to the control of the passive buzzer.
 */
public class PassiveBuzzerHelper {

    private static final Logger log = LoggerFactory.getLogger(PassiveBuzzerHelper.class);

    private final Pwm passiveBuzzer;
    /**
     * passiveBuzzerFreq is a protected integer variable that defines the default frequency for the buzzer.
     */
    protected int passiveBuzzerFreq = 440;

    /**
     * passBuzzDC is a protected integer variable that defines the duty cycle. When set at 50% the buzzer is in a half
     * on and half off state.
     */
    protected int passBuzzDC = 50;
    /**
     * The PassiveBuzzerHelper constructor.
     * @param passiveBuzzer A {@link  com.opensourcewithslu.utilities.PwmConfiguration} Object.
     */
    public PassiveBuzzerHelper( Pwm passiveBuzzer){

        this.passiveBuzzer = passiveBuzzer;

    }

    /**
     * Sets the passive buzzer to the desired duty cycle and frequency.
     * @param passBuzzDC sets the passive buzzer to the desired duty cycle.
     * @param passiveBuzzerFreq sets the passive buzzer to the desired frequency.
     */
    //tag::method[]
    public void passiveBuzzerOn(int passBuzzDC, int passiveBuzzerFreq){
    //end::method[]
        log.info("Initializing passive buzzer.");

        this.passiveBuzzer.on(passBuzzDC, passiveBuzzerFreq);
    }

    /**
     * Disables the passive buzzer. Effectively silencing it.
     */
    //tag::method[]
    public void passiveBuzzerOff(){
    //end::method[]

        log.info("Powering passive buzzer off.");

        this.passiveBuzzer.off();
    }

    /**
     *
     * Logs the passiveBuzzerFreq to the console
     */

    //tag::method[]
    public void getFrequency(){
    //end::method[]

        log.info(String.valueOf(passiveBuzzerFreq));

    }

    /**
     * Allows users to pipe in a text file of frequencies separated by commas to play on the passive buzzer.
     *
     * @param frequenciesFile The file containing the frequencies to be played
     */
    //tag::method[]
    public void setFrequencies(File frequenciesFile){
    //end::method[]

        int duration = 1000; //Pre-defined duration length of 1-sec
       try {
           Scanner scanner = new Scanner(frequenciesFile);

           String frequenciesStr = scanner.nextLine();
           String[] frequenciesArr = frequenciesStr.split(",");
           int[] frequencies = new int[frequenciesArr.length];

           for (int i = 0; i < frequenciesArr.length; i++) {
               frequencies[i] = Integer.parseInt(frequenciesArr[i].trim());
           }

           scanner.close();

           for (int frequency : frequencies) {
               if (frequency >= 20 && frequency <= 20000) {
                   log.info("Setting frequency to " + frequency + " Hz.");
                   passiveBuzzerOn(passBuzzDC, frequency);
                   try {
                       Thread.sleep(duration); // Play each frequency for the specified duration
                   } catch (InterruptedException e) {
                       Thread.currentThread().interrupt();
                   }
                   passiveBuzzerOff();
               } else {
                   log.error("Frequency is out of range. Please choose a value between 20 Hz and 20 kHz.");
               }
           }
       } catch (FileNotFoundException e) {
           log.error("Frequencies file not found: " + frequenciesFile.getAbsolutePath());
       }
    }

    /**
     * passiveBuzzTone emits a 1 - second buzz to ensure functionality
     */
    public void passiveBuzzTone(){
        passiveBuzzerOn(passBuzzDC, passiveBuzzerFreq);

        try{
            Thread.sleep(1000); // Buzz for 1 second
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        passiveBuzzerOff();
    }

    /**
     * freChangeTest cycles through frequencies to verify that frequencies are changing
     */
    public void toneIterator(){
        int [] frequencies  = { 880, 1760, 3520,9000,15000}; //Can add more frequencies to test
        for (int freq : frequencies){
            //passiveBuzzer.setFrequency(freq);
            log.info(String.valueOf(freq));
            passiveBuzzerOn(passBuzzDC, freq);
            try{
                Thread.sleep(1000); //Play each frequency for a full second
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
            passiveBuzzerOff();
        }
    }

    /**
     * Tone sequence cycles through array's containing the frequencies of the first
     * then digits of pi.
     */

    //tag::method[]
    public void piToneSequence(){
    //end::method[]

        int [] digitsOfPi = {3, 1, 4, 1, 5, 9, 2, 6, 5};
        int [] frequencies = {261, 293, 329, 349, 392, 440, 493, 523, 587, 659};

        passiveBuzzerOn(passBuzzDC, passiveBuzzerFreq);

        for (int digit : digitsOfPi){
            int freq = frequencies[digit];
            passiveBuzzerOn(passBuzzDC, freq);
            //passiveBuzzer.setFrequency(freq); This is no longer needed.
            try{
                Thread.sleep(500); //Pause for beat
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }

        try{
            Thread.sleep(1000); //Pause for a second after song concludes
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        passiveBuzzerOff();
    }


}
