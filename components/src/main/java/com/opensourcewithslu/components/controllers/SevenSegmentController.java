package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.SevenSegmentDisplayHelper;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

//tag::ex[]
@Controller("/seven-segment")
public class SevenSegmentController {

    private final SevenSegmentDisplayHelper displayHelper;

    public SevenSegmentController(@Named("pinA") DigitalOutput pinA,
                                  @Named("pinB") DigitalOutput pinB,
                                  @Named("pinC") DigitalOutput pinC,
                                  @Named("pinD") DigitalOutput pinD,
                                  @Named("pinE") DigitalOutput pinE,
                                  @Named("pinF") DigitalOutput pinF,
                                  @Named("pinG") DigitalOutput pinG,
                                  @Named("decimalPoint") DigitalOutput decimalPoint) {
        this.displayHelper = new SevenSegmentDisplayHelper(pinA, pinB, pinC, pinD, pinE, pinF, pinG, decimalPoint);
    }

    @Get("/enable")
    public void enable() {
        displayHelper.enable();
    }

    @Get("/disable")
    public void disable() {
        displayHelper.disable();
    }

    @Get("/print/{value}/{decimalPoint}")
    public void print(char value, boolean decimalPoint) {
        displayHelper.print(value, decimalPoint);
    }

    @Get("/clear")
    public void clear() {
        displayHelper.clear();
    }
}
//end::ex[]
