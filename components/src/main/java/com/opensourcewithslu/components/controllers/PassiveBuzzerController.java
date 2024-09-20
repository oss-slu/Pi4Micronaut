package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.PassiveBuzzerHelper;
import com.pi4j.io.pwm.Pwm;
import io.micronaut.http.annotation.*;
import jakarta.inject.Named;
import java.io.File;

//tag::ex[]
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
     *  Takes one file arg, function will allow use to set their own frequencies
     *  to be played by the passive buzzer. In order for the frequencies to be played
     *  the user must do the following:
     *  - Place their frequencies into a text file with the frequencies separated by commas
     *  - use the scp command to copy the file over to the raspberrypi
     *    - (i.e.: scp C:\Users\CompName\filename.txt name@raspberrypiname.local:/home/CompName)
     *  - Once file is copied over to the pi use the curl -X POST command to play the file
     *    - (i.e.: curl -X POST "http://localhost:8080/passive-buzzer/setFreq/filename.txt"
     */

    @Post("/setFreq/{frequenciesFile}")
    public void defineFrequency(String frequenciesFile){
        passiveBuzzerHelper.setFrequencies(new File(frequenciesFile));
    }

    /**
     * Validates the functionality of the passive buzzer
     */
    @Get("/passBuzz")
    public void singlePassiveBuzz(){
        passiveBuzzerHelper.passiveBuzzTone();
    }

    /**
     * Ensures that the passive buzzer can cycle through different frequencies
     */
    @Get("/freqIter")
    public void passiveFreqIter(){
        passiveBuzzerHelper.toneIterator();
    }

    /**
     * Calls toneSequence function to play a pre-defined song.
     */
    @Get("/playPiSeq")
    public void playPiTone(){

        passiveBuzzerHelper.piToneSequence();

    }
}
//end::ex[]