package com.opensourcewithslu.components.controllers;

/*Import statements can be updated as file structure progresses
---------------------------------------------------------------

 */
//import com.pi4j.component.sevseg.SevenSegment;
//import com.pi4j.crowpi.components.SevenSegmentComponent;
import com.opensourcewithslu.outputdevices.SevenSegmentDisplayHelper;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;



@Controller("/7SegDisplay")
public class SevenSegmentDisplayController {

    private final SevenSegmentDisplayHelper sevensegmentdisplayHelper; //Why is this not initialized?

    public SevenSegmentDisplayController(@Named("7SegmentDisplay")DigitalOutput sevsegdisplay, Context pi4jContext){
        this.sevensegmentdisplayHelper = new SevenSegmentDisplayHelper(sevsegdisplay,pi4jContext);
    }

    /* The following functions will be used here:
        * print (int i)
        * print (string s)
        *
     */

}
