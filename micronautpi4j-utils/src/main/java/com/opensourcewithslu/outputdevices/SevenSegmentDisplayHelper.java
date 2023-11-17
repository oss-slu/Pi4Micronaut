package com.opensourcewithslu.outputdevices;

import com.pi4j.context.Context;
import com.pi4j.crowpi.components.SevenSegmentComponent;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.i2c.I2CConfig;

import java.util.HashMap;

public class SevenSegmentDisplayHelper {

    private final SevenSegmentComponent segment;

    public  SevenSegmentDisplayHelper (I2CConfig i2CConfig, Context pi4j) {
        segment = new SevenSegmentComponent(pi4j, i2CConfig.bus(), i2CConfig.device());
        segment.setEnabled(true);

        //Disable blinking
        segment.setBlinkRate(0);
        segment.setBrightness(15);

        segment.setDigit (0, '8'); //This sets the digit in the zero position aka the only available pos.
        segment.setDecimalPoint(0, true); //Sets the decimal point for the display

        segment.refresh(); //refresh the display

        try {
            Thread.sleep(2000); // Using Thread.sleep for clarity
        } catch (InterruptedException e) {
            // Handle the interrupted exception
            Thread.currentThread().interrupt();
        }
        //Fake loading display

        HashMap<Character, Boolean> segmentStates = new HashMap<>();

        //Initialize the segments
        char [] segments = {'A', 'B', 'C', 'D', 'E', 'F'};
        int numberOfRotations = 1000;

        for (int i = 0; i < segments.length; i++) {
            segmentStates.put(segments[i], i % 2 == 0);
        }

        for (int i=0; i<numberOfRotations; i++) {
            for (char seg = 'A'; seg <= 'F'; seg++) {
                boolean lastState = segmentStates.get(segments[segments.length - 1]);
                for (int j = segments.length - 1; j > 0; j--) {
                    segmentStates.put(segments[j], segmentStates.get(segments[j - 1]));
                }
                segmentStates.put(segments[0], lastState);


                segment.refresh();

                try {
                    Thread.sleep(1000); // Using Thread.sleep for clarity
                } catch (InterruptedException d) {
                    // Handle the interrupted exception
                    Thread.currentThread().interrupt();
                }

            }
        }

    }
    public void printInteger(int i){
        segment.print(i);
    }

    public void printString(String s){
        segment.print(s);
    }

    public void countdownTimer(int value){
        /*User can choose integer and function will countdown from the starting point*/

        for (int i = value; i >= 0; i--){
            printInteger(value);
        }
    }

    public void numberCounter(int value){
        /*Value should be 0 for even numbers to be output and 1 for odd numbers to be output*/

            for (int i = value; i < 10; i += 2) {
                printInteger(value);
            }
    }

    public void showAllValues(){
        /*Function will display all values 0-9 then A-F*/
        for (int i = 0; i < 10; i ++) {
            printInteger(i);
        }

        for (int j= 65; j< 71; j++){
            printString(String.valueOf((char)j));
        }
    }

}
