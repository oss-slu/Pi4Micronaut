package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.BuzzerHelper;
import com.pi4j.io.pwm.Pwm;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

@Controller("/buzzer")
public class BuzzerController {

    private final BuzzerHelper buzzerHelper;

    public BuzzerController(@Named("active-buzzer") Pwm activeBuzzerOutput){
        this.buzzerHelper = new BuzzerHelper(activeBuzzerOutput);
    }

    /**
     * Enables the active buzzer
     */
    @Get("/enable")
    public void enableActiveBuzzer(){

            buzzerHelper.activeBuzzerOn();

    }

    /**
     * Disables the active buzzer
     */

    @Get("/disable")
    public void disableActiveBuzzer(){

            buzzerHelper.activeBuzzerOff();

    }

    /**
     * Emits an alert sound from the active buzzer.
     */
    @Get("/alertTone")
    public void playAlertTone(){

        buzzerHelper.alertTone();

    }

    /**
     * Emits a constant tone from the active buzzer for a duration of 20 seconds.
     * 10 seconds of sound and 10 seconds of silence
     */
    @Get("/constantTone")
    public void playConstantTone(){

        buzzerHelper.constantTone();

    }

    /**
     * Tests the active buzzer by emitting the word "pi" in morse code.
     */
    @Get("/testBuzzer-active")
    public void testBuzzer(){

        buzzerHelper.buzzerTest();
    }

    /**
     * The following functions need to go into a separate passive buzzer controller:
     * ----------------------------------------------------------------------------
     *
     *
     *     public void enablePassiveBuzzer(){
     *             buzzerHelper.passiveBuzzerOn();
     *
     *     }
     *
     *
     *     public void disablePassiveBuzzer(){
     *
     *             buzzerHelper.passiveBuzzerOff();
     *
     *     }
     *
     *
     *     public void sequenceOfTones(){
     *
     *         //Array can be moved or left in method
     *         int [] CL = {0, 131, 147, 165, 175, 196, 211, 248};
     *         int [] CM = {0, 262, 294, 330, 350, 393, 441, 495};
     *         int [] CH = {0, 525, 589, 661, 700, 786, 882, 990};
     *
     *         int [] song_1 = {CM[3], CM[5], CM[6], CM[3], CM[2], CM[3], CM[5], CM[6],
     *         CH[1], CM[6], CM[5], CM[1], CM[3], CM[2], CM[2], CM[3],
     *                 CM[5], CM[2], CM[3], CM[3], CL[6], CL[6], CL[6], CM[1],
     *                 CM[2], CM[3], CM[2], CL[7], CL[6], CM[1], CL[5]};
     *
     *         enablePassiveBuzzer();
     *         for (int tone : song_1){
     *             buzzerHelper.setFrequency(tone);
     *             try{
     *                 Thread.sleep(500); //Delay for the beat
     *             } catch (InterruptedException e){
     *                 Thread.currentThread().interrupt();
     *             }
     *         }
     *
     *         try{
     *             Thread.sleep(1000); //Pause for 1 second after song concludes
     *         } catch (InterruptedException e){
     *             Thread.currentThread().interrupt();
     *         }
     *         disablePassiveBuzzer();
     *     }
     *
     */
}
