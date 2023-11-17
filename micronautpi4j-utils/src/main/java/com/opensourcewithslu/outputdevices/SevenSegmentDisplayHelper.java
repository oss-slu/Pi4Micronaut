package com.opensourcewithslu.outputdevices;

import com.pi4j.context.Context;
import com.pi4j.crowpi.components.SevenSegmentComponent;
import com.pi4j.io.gpio.digital.DigitalOutput;

import java.util.HashMap;

//import java.time.LocalTime;
//Basic helper class for the Seven Segment Display. Uses crowpi components.
public class SevenSegmentDisplayHelper {

    public  SevenSegmentDisplayHelper (DigitalOutput seven, Context pi4j) {
        final var segment = new SevenSegmentComponent(pi4j);
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

}
