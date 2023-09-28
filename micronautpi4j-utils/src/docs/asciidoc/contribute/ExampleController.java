package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.ExamplHelper;
import com.opensourcewithslu.outputdevices.LEDHelper;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

//tag::ex[]
@Controller("/example")
public class ExampleController {
    private final PushButtonHelper pushButtonHelper;
    

    public ExampleController(ExamplHelper exampleHelper) {
        this.exampleHelper = exampleHelper;
    }

    @Get("/init")
    public void initController(){
        exampleHelper.addEventListener(e ->{
            /*Some action */
        });
    }
}
//end::ex[]