package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.RFidHelper;
import com.pi4j.context.Context;
import com.pi4j.io.spi.SpiConfig;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

@Controller("/rfid")
public class RfidController {

    private final RFidHelper rfidHelper;

    public RfidController(@Named("rfid") SpiConfig spi, Context pi4jContext){this.rfidHelper = new RFidHelper(spi, 25, pi4jContext);}

    @Get("/init")
    public void getEncoderValue(){rfidHelper.initialize();}
}