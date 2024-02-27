package com.opensourcewithslu.outputdevices;



import com.pi4j.io.pwm.Pwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ActiveBuzzerHelper class contains methods that pertain to the control of the active buzzer.
 *
 * IMPORTANT NOTE: WIRING MUST BE DIRECT. USAGE OF TRANSISTOR ALTERS THE FUNCTIONALITIES OF THE HELPER.
 */
public class ActiveBuzzerHelper {

    private static final Logger log = LoggerFactory.getLogger(ActiveBuzzerHelper.class);

    private final Pwm activeBuzzer;
    /**
     * To check if the buzzer is active or not
     */
    protected boolean actBuzzCheck;


    /**
     * BuzzerHelper constructor
     * @param activeBuzzer instance of a Pwm object
     */
    //tag::const[]
    public ActiveBuzzerHelper( Pwm activeBuzzer){
    //end::const[]

        this.activeBuzzer = activeBuzzer;
    }


    /**
     * Turns the active buzzer on by setting the duty cycle is 100 and frequency to 440hz.
     */
    //tag::method[]
    public void activeBuzzerOn(){
        //end::method[]
        log.info("Active buzzer is on.");


        this.activeBuzzer.on(100, 440); //Duty Cycle must be 100 and Frequency should be 440.
        actBuzzCheck = true;


    }
    /**
     * Turns the active buzzer off.
     */
    //tag::method[]
    public void activeBuzzerOff(){
    //end::method[]
        log.info("Active Buzzer is off.");

        this.activeBuzzer.off();

        actBuzzCheck = false;

    }

    /**
     * Beep powers on, plays a single tone from the active buzzer for 2 seconds then powers down.
     */
    //tag::method[]
    public void beep(){
    //end::method[]

        activeBuzzerOff(); //Turn off buzzer in case it's on

        log.info("Beeps for 2 seconds.");
        activeBuzzerOn();
        try {
            Thread.sleep(2000); //Wait and alert once for 2 seconds (2000 milliseconds)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        activeBuzzerOff();

    }

    /**
     * Intermittent tone will play a tone for a 20 seconds duration. During this duration the
     * buzzer will be on for 10 seconds and off for 10 seconds.
     */
    //tag::method[]
    public void intermittentTone(){
    //end::method[]

        int sleepDuration = 1000; //1000 milliseconds equals 1 second
        int totalActiveDur = 0;
        activeBuzzerOff();

        log.info("Buzzer turns on for 10 seconds.");
        while (true){
            activeBuzzerOn();
            try{
                Thread.sleep(sleepDuration);
                totalActiveDur += sleepDuration;

            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
            activeBuzzerOff();
            try{
                Thread.sleep(sleepDuration);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
            if (totalActiveDur == 10000){
                break;
            }
        }
    }

    /**
     * Uses the active buzzer on and off function to beep the word pi in morse code.
     */
    //tag::method[]
    public void morseCodeTone(){
    //end::method[]
        int [] morseCode = {200,600,600,200,200,200}; // Durations for .--. .. (pi in Morse)
        int gapDuration = 200; //Gap between the signals
        activeBuzzerOff();

        log.info("Playing morse code for 'Pi'.");
        for (int duration : morseCode){
            activeBuzzerOn();
            try{
                Thread.sleep(duration); //Play the tone
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
            activeBuzzerOff();
            try{
                Thread.sleep(gapDuration); //Pause between tones
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}
