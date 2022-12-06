package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.RotaryEncoderHelper;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
@Controller("/rotaryEncoder")
public class RotaryEncoderController {

    private final RotaryEncoderHelper encoderHelper;

    public RotaryEncoderController(RotaryEncoderHelper encoderHelper){this.encoderHelper = encoderHelper;}

    @Get("/value")
    public int getEncoderValue(){return encoderHelper.getEncoderValue();}
}