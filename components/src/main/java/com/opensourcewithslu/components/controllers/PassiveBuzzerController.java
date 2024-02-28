package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.PassiveBuzzerHelper;
import com.pi4j.io.pwm.Pwm;
import io.micronaut.http.annotation.*;
import jakarta.inject.Named;

@Controller("/passive-buzzer")
public class PassiveBuzzerController {

    private final PassiveBuzzerHelper passiveBuzzerHelper;

    protected int passBuzzFreq = 440;

    protected int passBuzzDC = 50;

    public PassiveBuzzerController(@Named("passive-buzzer") Pwm passiveBuzzerOutput){
        this.passiveBuzzerHelper = new PassiveBuzzerHelper(passiveBuzzerOutput);
    }

    /**
     * Enables passive buzzer
     */

    @Get("/enable")
    public void enablePassiveBuzzer(){

        passiveBuzzerHelper.passiveBuzzerOn(passBuzzDC, passBuzzFreq);

    }

    /**
     * Disables passive buzzer
     */
    @Get("/disable")
    public void disablePassiveBuzzer(){

        passiveBuzzerHelper.passiveBuzzerOff();

    }

    /**
     *
     * Displays the current frequency of the passive buzzer.
     */

    @Get("/showFreq")
    public void passiveBuzzerFreq(){

        passiveBuzzerHelper.getFrequency();

    }

    /**
     *
     *  Set frequency to given integer; FUNCTION STILL NEEDS WORK, NON-FUNCTIONAL CURRENTLY
     */

    @Post("/setFreq/{frequencies},{duration}")
    public void defineFrequency(int[] frequencies, int duration){
        passiveBuzzerHelper.setFrequencies(frequencies,duration);
    }

    /**
     * Validates the functionality of the passive buzzer
     */
    @Get("/funcTest")
    public void passiveFuncTest(){
        passiveBuzzerHelper.functionalityTest();
    }

    /**
     * Ensures that the passive buzzer can cycle through different frequencies
     */
    @Get("/freqTest")
    public void passiveFreqTest(){
        passiveBuzzerHelper.freqChangeTest();
    }

    /**
     * Calls toneSequence function to play a pre-defined song.
     */
    @Get("/playPiSeq")
    public void playPiTone(){

        passiveBuzzerHelper.piToneSequence();

    }
}