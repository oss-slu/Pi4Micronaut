package com.opensourcewithslu.outputdevices;


import com.pi4j.io.pwm.Pwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The BuzzerHelper class contains methods that pertain to the control of the active and passive buzzers.
 */
public class ActiveBuzzerHelper {

    private static final Logger log = LoggerFactory.getLogger(ActiveBuzzerHelper.class);

    private final Pwm activeBuzzer;

    //private final Pwm passiveBuzzerOutput;



    //private int passiveBuzzerFrequency = 440 ; //440Hz is commonly used standard pitch frequency

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


            this.activeBuzzer.on(50,440); //define duty cycle and frequency

    }
    /**
     * Turns the active buzzer off by .
     */
    //tag::method[]
    public void activeBuzzerOff(){
    //end::method[]
        log.info("Active Buzzer is off.");

        this.activeBuzzer.off();
    }

    public void alertTone(){

        activeBuzzerOn();

        try{
            Thread.sleep(2000); //Wait and alert once for 2 seconds (2000 milliseconds)
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        activeBuzzerOff();
    }

    public void constantTone(){

        int sleepDuration = 1000; //1000 milliseconds equals 1 second
        int totalActiveDur = 0;

        while (true){
            log.info("Buzzer on.");
            activeBuzzerOn();
            try{
                Thread.sleep(sleepDuration);
                totalActiveDur += sleepDuration;
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
            log.info("Buzzer off.");
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

    public void buzzerTest(){

        int [] morseCodePi = {200,600,600,200,200,200}; // Durations for .--. .. (pi in Morse)
        int gapDuration = 200; //Gap between the signals

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
