package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.BuzzerHelper;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

@Controller("/buzzer")
public class BuzzerController {

    private final BuzzerHelper buzzerHelper;

    public BuzzerController(@Named("buzzer-Output") DigitalOutput buzzerOutput){
        this.buzzerHelper = new BuzzerHelper(buzzerOutput);
    }

    /**
     * Enables the passive buzzer
     */
    @Get("/enable-passive")
    public void enablePassiveBuzzer(){
            buzzerHelper.passiveBuzzerOn();

    }

    /**
     * Disables the passive buzzer.
     */

    @Get("/disable-passive")
    public void disablePassiveBuzzer(){

            buzzerHelper.passiveBuzzerOff();

    }

    /**
     * Enables the active buzzer
     */
    @Get("/enable-active")
    public void enableActiveBuzzer(){

            buzzerHelper.activeBuzzerOn();

    }

    /**
     * Disables the active buzzer
     */

    @Get("/disable-active")
    public void disableActiveBuzzer(){

            buzzerHelper.activeBuzzerOff();

    }

    /**
     * Emits an alert sound from the active buzzer.
     */
    @Get("/alertTone-active")
    public void playAlertTone(){

        enableActiveBuzzer();

        try{
            Thread.sleep(2000); //Wait and alert for 2 seconds (2000 milliseconds)
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        disableActiveBuzzer();
    }

    /**
     * Plays a sequence of tones via a predefined array.
     */
    @Get("/toneSequence-passive")
    public void sequenceOfTones(){

        //Array can be moved or left in method
        int [] CL = {0, 131, 147, 165, 175, 196, 211, 248};
        int [] CM = {0, 262, 294, 330, 350, 393, 441, 495};
        int [] CH = {0, 525, 589, 661, 700, 786, 882, 990};

        int [] song_1 = {CM[3], CM[5], CM[6], CM[3], CM[2], CM[3], CM[5], CM[6],
        CH[1], CM[6], CM[5], CM[1], CM[3], CM[2], CM[2], CM[3],
                CM[5], CM[2], CM[3], CM[3], CL[6], CL[6], CL[6], CM[1],
                CM[2], CM[3], CM[2], CL[7], CL[6], CM[1], CL[5]};

        for (int tone : song_1){
            buzzerHelper.setFrequency(tone);
            try{
                Thread.sleep(500); //Delay for the beat
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }

        try{
            Thread.sleep(1000); //Pause for 1 second after song concludes
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Tests the active buzzer by emitting the word "pi" in morse code.
     */
    @Get("/testBuzzer-active")
    public void testBuzzer(){

        int [] morseCodePi = {200,600,600,200,200,200}; // Durations for .--.. (pi in Morse)
        int gapDuration = 200; //Gap between the signals

        for (int duration : morseCodePi){
            enableActiveBuzzer();
            try{
                Thread.sleep(duration); //Play the tone
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
            disableActiveBuzzer();
            try{
                Thread.sleep(gapDuration); //Pause between tones
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}
