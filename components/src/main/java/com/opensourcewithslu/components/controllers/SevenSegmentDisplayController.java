package com.opensourcewithslu.components.controllers;

/*Import statements can be updated as file structure progresses
---------------------------------------------------------------
import com.pi4j.component.sevseg.SevenSegment;
import com.pi4j.crowpi.components.SevenSegmentComponent;
import com.opensourcewithslu.outputdevices.SevenSegmentDisplayHelper;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;
*/


@Controller("/7SegDisp")
public class SevenSegmentDisplayController {

    private final SevenSegmentDisplayHelper sevensegmentdisplayHelper;

    public SevenSegmentDisplayController(@Named("7SegmentDisplay")DigitalOutput sevsegdisplay){
        this.sevensegmentdisplayHelper = new SevenSegmentDisplayHelper;
    }

    /* The following functions will be used here:
        * print (int i)
        * print (string s)
        * SetDigit(s)?
        * SetRawDigit?
     */

}
