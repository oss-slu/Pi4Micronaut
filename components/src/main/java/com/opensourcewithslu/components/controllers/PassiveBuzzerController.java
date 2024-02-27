package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.PassiveBuzzerHelper;
import com.pi4j.io.pwm.Pwm;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.PathVariable;
import jakarta.inject.Named;

@Controller("/passive-buzzer")
public class PassiveBuzzerController {

    private final PassiveBuzzerHelper passiveBuzzerHelper;

    public PassiveBuzzerController(@Named("passive-buzzer") Pwm passiveBuzzerOutput){
        this.passiveBuzzerHelper = new PassiveBuzzerHelper(passiveBuzzerOutput);
    }

    /**
     * Enables passive buzzer
     */

    @Get("/enable")
    public void enablePassiveBuzzer(){

        passiveBuzzerHelper.passiveBuzzerOn();

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
     * @param i will set frequency to given integer
     */

    @Post("/setFreq/{i}")
    public void defineFrequency(@PathVariable int i){
        passiveBuzzerHelper.setFrequency(i);
    }

    /**
     * Calls toneSequence function to play a pre-defined song.
     */
    @Get("/playSeqOfTones")
    public void playSequence(){

        passiveBuzzerHelper.toneSequence();

    }
}