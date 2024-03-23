package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.ActiveBuzzerHelper;
import com.pi4j.io.pwm.Pwm;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

@Controller("/active-buzzer")
public class ActiveBuzzerController {

    private final ActiveBuzzerHelper activeBuzzerHelper;

    public ActiveBuzzerController(@Named("active-buzzer") Pwm activeBuzzerOutput){
        this.activeBuzzerHelper = new ActiveBuzzerHelper(activeBuzzerOutput);
    }

    /**
     * Enables the active buzzer
     */
    @Get("/enable")
    public void enableActiveBuzzer(){

        activeBuzzerHelper.activeBuzzerOn();

    }

    /**
     * Disables the active buzzer
     */

    @Get("/disable")
    public void disableActiveBuzzer(){

        activeBuzzerHelper.activeBuzzerOff();

    }

    /**
     * Emits a beep sound from the active buzzer.
     */
    @Get("/beepTone")
    public void playBeepTone(){

        activeBuzzerHelper.beep();

    }

    /**
     * Emits an intermittent tone from the active buzzer for a duration of 20 seconds.
     * 10 seconds of sound and 10 seconds of silence
     */
    @Get("/intermittentTone")
    public void playIntermittentTone(){

        activeBuzzerHelper.intermittentTone();

    }

    /**
     * Tests the active buzzer by emitting the word "pi" in morse code.
     */
    @Get("/morseCode")
    public void morseCodeTest(){

        activeBuzzerHelper.morseCodeTone();
    }
}