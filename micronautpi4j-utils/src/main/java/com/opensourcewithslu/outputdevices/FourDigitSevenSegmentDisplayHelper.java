package com.opensourcewithslu.outputdevices;

import com.opensourcewithslu.utilities.MultipinConfiguration;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;

//import com.pi4j.io.gpio.*;
//import com.pi4j.wiringpi.Gpio; replace with pigpio

import java.util.Timer;
import java.util.TimerTask;


// OG code from: https://learn.sunfounder.com/lesson-1-1-5-4-digit-7-segment-display/
// Java version from chatgpt.
public class FourDigitSevenSegmentDisplayHelper {
    private static final int SDI = 5; //Serial digital interface
    private static final int RCLK = 4; //reference clock
    private static final int SRCLK = 1; // serial clock

    private static  MultipinConfiguration placePin; //pins, t

    private static Context pi4jContext;

    /// hex codes for the numbers       0,1,2,3,4,5,6,7,8,9
    private static final int[] number = {0xc0, 0xf9, 0xa4, 0xb0, 0x99, 0x92, 0x82, 0xf8, 0x80, 0x90};

    private static int counter = 0;


    // I think these two need to be converted to something from the Pi4JFactory
    //private static GpioController gpio; // replace with pi4jcontext?
    private static DigitalOutput[] digitPins;


    //tag::const[]
    public FourDigitSevenSegmentDisplayHelper(MultipinConfiguration fourdigsevenseg, Context pi4jContext)
    //end::const[]
    {
        placePin = fourdigsevenseg; // = {12, 3, 2, 0}, the pins are gotten from the multipin parameter.

         this.pi4jContext = pi4jContext;

        // Initialize Pi4J
        //Gpio.wiringPiSetup();

        // Initialize GPIO pins
        //gpio = GpioFactory.getInstance();
        initializePins();

    }
    //tag::method[]
    public void test()
    //end::method[]
    {


        // Set up a timer
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                counter++;
                updateDisplay();
                System.out.println(counter);
            }
        }, 0, 1000); // Timer updates every 1000 milliseconds (1 second)

        // Keep the program running
        while (true) {
            // Do nothing, let the timer handle the display updates
        }
    }



    private static void initializePins() {
        digitPins = new DigitalOutput[4];

        for (int i = 0; i < 4; i++) {
            digitPins[i] = gpio.gpioSetMode(RaspiPin.getPinByAddress(placePin[i]), PinState.LOW);
        }

        Gpio.pinMode(SDI, Gpio.OUTPUT);
        Gpio.pinMode(RCLK, Gpio.OUTPUT);
        Gpio.pinMode(SRCLK, Gpio.OUTPUT);
    }

    private static void updateDisplay() {
        for (int i = 0; i < 4; i++) {
            clearDisplay();
            pickDigit(i);
            hc595Shift(number[counter % (int) Math.pow(10, i + 1) / (int) Math.pow(10, i)]);
        }
    }

    private static void pickDigit(int digit) {
        for (int i = 0; i < 4; i++) {
            digitPins[i].setState(i == digit);
        }
    }

    private static void hc595Shift(int data) {
        for (int i = 7; i >= 0; i--) {
            Gpio.digitalWrite(SDI, (data & (1 << i)) != 0 ? Gpio.HIGH : Gpio.LOW);
            Gpio.digitalWrite(SRCLK, Gpio.HIGH);
            sleep(1);
            Gpio.digitalWrite(SRCLK, Gpio.LOW);
        }

        Gpio.digitalWrite(RCLK, Gpio.HIGH);
        sleep(1);
        Gpio.digitalWrite(RCLK, Gpio.LOW);
    }

    private static void clearDisplay() {
        for (int i = 0; i < 8; i++) {
            Gpio.digitalWrite(SDI, Gpio.HIGH);
            Gpio.digitalWrite(SRCLK, Gpio.HIGH);
            sleep(1);
            Gpio.digitalWrite(SRCLK, Gpio.LOW);
        }

        Gpio.digitalWrite(RCLK, Gpio.HIGH);
        sleep(1);
        Gpio.digitalWrite(RCLK, Gpio.LOW);
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
