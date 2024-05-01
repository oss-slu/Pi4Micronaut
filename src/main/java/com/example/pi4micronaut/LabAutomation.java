package com.example.pi4micronaut;

import com.opensourcewithslu.inputdevices.*;
import com.opensourcewithslu.outputdevices.*;

import com.pi4j.context.Context;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.i2c.I2CConfig;
import io.micronaut.http.annotation.Controller;
import jakarta.inject.Named;

@Controller("/LabAutomation")
public class LabAutomation {
    private final LCD1602Helper lcd;
    private final LEDHelper led;
    private final UltraSonicSensorHelper ultraSonicSensorStart;
    private final UltraSonicSensorHelper ultraSonicSensorEnd;

    public LabAutomation(
            @Named("lcd") I2CConfig lcd,
            @Named("led") DigitalOutput led,
            @Named("ultra-sonic-trig1") DigitalOutput trig1,
            @Named("ultra-sonic-echo1") DigitalInput echo1,
            @Named("ultra-sonic-trig2") DigitalOutput trig2,
            @Named("ultra-sonic-echo2") DigitalInput echo2,
            Context pi4j)
    {
        this.lcd = new LCD1602Helper(lcd,pi4j);
        this.led = new LEDHelper(led);
        this.ultraSonicSensorStart = new UltraSonicSensorHelper(trig1,echo1);
        this.ultraSonicSensorEnd = new UltraSonicSensorHelper(trig2,echo2);
    }

    /*
        For the Lab automation, we are going to assume the mouse maze.
        The idea of this maze is to figure out how much time the mouse takes to exit the maze.
        We are going to use 2 ultrasonic sensors at the entry and exit locations.
        - Start and end time need to be noted based on the ultrasonic sensor's input( should have a threshold of 4 CM).
        - Subtract end time with the start time to get the time taken by the mouse to exit.
        - Use LCD to display messages like Mouse entered, mouse left and total time
     */

}
