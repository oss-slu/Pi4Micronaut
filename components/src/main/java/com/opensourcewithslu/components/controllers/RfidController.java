package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.RFidHelper;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

//tag::ex[]
@Controller("/rfid")
public class RfidController {

    private final RFidHelper rfidHelper;

    public RfidController(RFidHelper rfid){
        this.rfidHelper = rfid;
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