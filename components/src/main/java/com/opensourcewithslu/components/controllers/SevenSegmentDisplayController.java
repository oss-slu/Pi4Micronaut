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



@Controller("/7SegmentDisplay")
public class SevenSegmentDisplayController {

    private final SevenSegmentDisplayHelper sevensegmentdisplayHelper; //Why is this not initialized?

    public SevenSegmentDisplayController(@Named("SevenSegmentDisplay")DigitalOutput sevsegdisplay, Context pi4jContext){
        this.sevensegmentdisplayHelper = new SevenSegmentDisplayHelper(sevsegdisplay,pi4jContext);
    }

    @Get("/printInt")
    public void printInt(int i){
        /*Outputs the desired integer value*/
        sevensegmentdisplayHelper.printInteger(i);
    }
    @Get("/printStr")
    public void printString(String s){
        /*Outputs the desired string value*/
        sevensegmentdisplayHelper.printString(s);
    }

    @Get("/countdown")
    public void countdown(int val){
        /*Outputs values in a countdown fashion, user defines the value to begin at*/
        sevensegmentdisplayHelper.countdownTimer(val);
    }

    @Get("/numCtr")
    public void numberCounter(int val){
        /*Outputs even or odd values as specified by the user*/

        sevensegmentdisplayHelper.numberCounter(val);
    }

    @Get("/dispAllVals")
    public void displayAllValues(){
        /*Outputs all values from 0-9 & A-F*/
        sevensegmentdisplayHelper.showAllValues();
    }

}
