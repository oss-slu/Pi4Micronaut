package com.opensourcewithslu.components.controllers;

/*Import statements can be updated as file structure progresses
---------------------------------------------------------------

 */
//import com.pi4j.component.sevseg.SevenSegment;
//import com.pi4j.crowpi.components.SevenSegmentComponent;
import com.pi4j.io.i2c.I2CConfig;
import com.opensourcewithslu.outputdevices.SevenSegmentDisplayHelper;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;



@Controller("/7SegmentDisplay")
public class SevenSegmentDisplayController {

    private final SevenSegmentDisplayHelper sevensegmentdisplayHelper; //Why is this not initialized?

    public SevenSegmentDisplayController(@Named("SevenSegmentDisplay") I2CConfig i2CConfig, Context pi4jContext){
        this.sevensegmentdisplayHelper = new SevenSegmentDisplayHelper(i2CConfig,pi4jContext);
    }

    @Get("/printInt/{i}")
    public void printInt(int i){
        /*Outputs the desired integer value*/
        sevensegmentdisplayHelper.printInteger(i);
    }
    @Get("/printStr/{s}")
    public void printString(String s){
        /*Outputs the desired string value*/
        sevensegmentdisplayHelper.printString(s);
    }

    @Get("/countdown/{val}")
    public void countdown(int val){
        /*Outputs values in a countdown fashion, user defines the value to begin at*/
        sevensegmentdisplayHelper.countdownTimer(val);
    }

    @Get("/numCtr/{val}")
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
