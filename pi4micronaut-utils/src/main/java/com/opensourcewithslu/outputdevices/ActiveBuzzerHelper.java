package com.opensourcewithslu.outputdevices;


//import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.pwm.Pwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * The BuzzerHelper class contains methods that pertain to the control of the active and passive buzzers.
 *
 * IMPORTANT NOTE: WIRING MUST BE DIRECT. USAGE OF TRANSISTOR ALTERS THE FUNCTIONALITES OF THE HELPER.
 */
public class ActiveBuzzerHelper {

    private static final Logger log = LoggerFactory.getLogger(ActiveBuzzerHelper.class);

    private final Pwm activeBuzzer;

    protected boolean actBuzzCheck;


    /**
     * BuzzerHelper constructor
     * @param activeBuzzer instance of a Pwm object
     */
    //tag::const[]
    public ActiveBuzzerHelper( Pwm activeBuzzer){
        //end::const[]

        this.activeBuzzer = activeBuzzer;
        // this.passiveBuzzerOutput = passiveBuzzerOutput;
    }


    /**
     * Turns the active buzzer on by setting the duty cycle is 50 and frequency to 440hz.
     */
    //tag::method[]
    public void activeBuzzerOn(){
        //end::method[]
        log.info("Active buzzer is on.");


        this.activeBuzzer.on(100, 3000); //Duty Cycle must be 100 and Frequency should be 440.
        actBuzzCheck = true;


    }
    /**
     * Turns the active buzzer off by .
     */
    //tag::method[]
    public void activeBuzzerOff(){
        //end::method[]
        log.info("Active Buzzer is off.");

        this.activeBuzzer.off();

        actBuzzCheck = false;

    }

    public void alertTone(){
        activeBuzzerOff(); //Turn off buzzer in case it's on

        activeBuzzerOn();
        try {
            Thread.sleep(2000); //Wait and alert once for 2 seconds (2000 milliseconds)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        activeBuzzerOff();

    }

    public void constantTone(){

        int sleepDuration = 1000; //1000 milliseconds equals 1 second
        int totalActiveDur = 0;
        activeBuzzerOff();

        while (true){
            log.info("Buzzer on.");
            activeBuzzerOff();
            try{
                Thread.sleep(sleepDuration);
                totalActiveDur += sleepDuration;
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
            log.info("Buzzer off.");
            activeBuzzerOn();
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

    public void buzzerTest(){

        int [] morseCodePi = {200,600,600,200,200,200}; // Durations for .--. .. (pi in Morse)
        int gapDuration = 200; //Gap between the signals
        activeBuzzerOff();

        for (int duration : morseCodePi){
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
