package com.opensourcewithslu.outputdevices;

import com.pi4j.io.pwm.Pwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class PassiveBuzzerHelper {

    private static final Logger log = LoggerFactory.getLogger(PassiveBuzzerHelper.class);

    private final Pwm passiveBuzzer;

    protected int passiveBuzzerFreq = 440;

    public PassiveBuzzerHelper( Pwm passiveBuzzer){

        this.passiveBuzzer = passiveBuzzer;

    }

    /**
     * Initializes passive buzzer to a 50% duty cycle and frequency of 440Hz
     */
    //tag::method[]
    public void passiveBuzzerOn(){
    //end::method[]
        log.info("Initializing passive buzzer.");

        this.passiveBuzzer.on(100, passiveBuzzerFreq);
    }

    /**
     * Disables the passive buzzer. Effectively silencing it.
     */
    //tag::method[]
    public void passiveBuzzerOff(){
    //end::method[]
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
     *
     * @param frequency allows the user to change the frequency
     */
    //tag::method[]
    public void setFrequency(int frequency){
    //end::method[]
        if (frequency >= 20 && frequency <= 20000) {
            passiveBuzzerFreq = frequency;
        } else {
            log.error("Frequency is out of range. Please choose a value between 20 Hz and 20 Khz.");
        }
    }

    /**
     * Functionality test emits a 1 - second buzz to ensure functionality
     */
    public void functionalityTest(){
        passiveBuzzerOn();

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
    public void freqChangeTest(){
        int [] frequencies  = { 880, 1760, 3520}; //Can add more frequencies to test
        for (int freq : frequencies){
            passiveBuzzer.setFrequency(freq);
            passiveBuzzerOn();
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

        passiveBuzzerOn();

        for (int digit : digitsOfPi){
            int freq = frequencies[digit];
            passiveBuzzer.setFrequency(freq); //There is a pwm frequency method that can be used.
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
