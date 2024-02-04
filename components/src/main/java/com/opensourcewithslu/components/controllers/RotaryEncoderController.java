package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.RotaryEncoderHelper;
import com.opensourcewithslu.utilities.MultipinConfiguration;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

//tag::ex[]
@Controller("/rotaryEncoder")
public class RotaryEncoderController {
    private final RotaryEncoderHelper encoderHelper;

    public RotaryEncoderController(@Named("rotary-encoder")MultipinConfiguration rotaryEncoder){
        this.encoderHelper = new RotaryEncoderHelper(rotaryEncoder);
    }

    @Get("/value")
    public int getEncoderValue(){return encoderHelper.getEncoderValue();}
}
//end::ex[]