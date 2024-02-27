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

    //tag::method[]
    public void toneSequence(){
    //end::method[]

        //Array of frequencies for CMajor
        int [] CL = {0, 131, 147, 165, 175, 196, 211, 248};
        int [] CM = {0, 262, 294, 330, 350, 393, 441, 495};
        int [] CH = {0, 525, 589, 661, 700, 786, 882, 990};

        int [] song_1 = {CM[3], CM[5], CM[6], CM[3], CM[2], CM[3], CM[5], CM[6],
                CH[1], CM[6], CM[5], CM[1], CM[3], CM[2], CM[2], CM[3],
                CM[5], CM[2], CM[3], CM[3], CL[6], CL[6], CL[6], CM[1],
                CM[2], CM[3], CM[2], CL[7], CL[6], CM[1], CL[5]};

        passiveBuzzerOn();

        for (int tone : song_1){
            passiveBuzzer.setFrequency(tone); //There is a pwm frequency method that can be used.
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
