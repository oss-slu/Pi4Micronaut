package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.RFidHelper;
import com.pi4j.context.Context;
import com.pi4j.io.spi.SpiConfig;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Named;

//tag::ex[]
@Controller("/rfid")
public class RfidController {
    private final RFidHelper rfidHelper;

    public RfidController(@Named("rfid") SpiConfig spi, Context pi4jContext){
        this.rfidHelper = new RFidHelper(spi, 25, pi4jContext);
    }

    @Post("/write/{value}")
    public void writeToCard(String value){
        rfidHelper.writeToCard(value);
    }

    @Get("/read")
    public String readFromCard(){
        Object data = rfidHelper.readFromCard();
        return data.toString();
    }
}
//end::ex[]