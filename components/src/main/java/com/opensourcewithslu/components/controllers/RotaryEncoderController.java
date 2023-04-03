package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.RotaryEncoderHelper;
import com.opensourcewithslu.utilities.MultipinConfiguration;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

@Controller("/rotaryEncoder")
public class RotaryEncoderController {

    private final RotaryEncoderHelper encoderHelper;
    private final RotaryEncoderHelper encoderHelper2;

    public RotaryEncoderController(@Named("rotary-encoder")MultipinConfiguration rotaryEncoder, @Named("rotary-encoder-2")MultipinConfiguration rotaryEncoder2){
        this.encoderHelper = new RotaryEncoderHelper(rotaryEncoder);
        this.encoderHelper2 = new RotaryEncoderHelper(rotaryEncoder2);
    }

    @Get("/value")
    public int getEncoderValue(){return encoderHelper.getEncoderValue();}
}