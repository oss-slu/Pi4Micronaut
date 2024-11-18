package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.outputdevices.SevenSegmentDisplayHelper;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
//tag::ex[]
@Controller("/sevensegment")
public class SevenSegmentController {

    private final SevenSegmentDisplayHelper displayHelper;

    // Inject SevenSegmentDisplayHelper as a dependency
    @Inject
    public SevenSegmentController(SevenSegmentDisplayHelper displayHelper) {
        this.displayHelper = displayHelper;
    }

    @Get("/display/{number}")
    public void displayNumber(int number) {
        displayHelper.display(number);
    }

    @Get("/reset")
    public void resetDisplay() {
        displayHelper.resetDisplay();
    }

    @Get("/shutdown")
    public void shutdownDisplay() {
        displayHelper.shutdown();
    }
}
//end::ex[]