package com.example.pi4micronaut;

import com.opensourcewithslu.inputdevices.*;
import com.opensourcewithslu.outputdevices.*;
import com.opensourcewithslu.utilities.MultipinConfiguration;


import com.pi4j.context.Context;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.i2c.I2CConfig;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

import java.lang.Thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/LabAutomation")
public class LabAutomation {
    private final LCD1602Helper lcd;
    private final RGBLEDHelper rgb;
    private final PIRSensorHelper pirStart;
    private final PIRSensorHelper pirEnd;

    private Thread timer;
    private boolean inMaze;
    private int timeInMaze;
    private long startTime;
    private long endTime;

    private static final Logger log = LoggerFactory.getLogger(LabAutomation.class);

    public LabAutomation(
            @Named("lcd") I2CConfig lcd,
            @Named("rgb-led") MultipinConfiguration rgb,
            @Named("pir-sensor-1") DigitalInput pirStart,
            @Named("pir-sensor-2") DigitalInput pirEnd,
            Context pi4j)
    {
        this.lcd = new LCD1602Helper(lcd,pi4j);
        this.rgb = new RGBLEDHelper(rgb);
        this.pirStart = new PIRSensorHelper(pirStart);
        this.pirEnd = new PIRSensorHelper(pirEnd);

        this.inMaze = false;
        this.timeInMaze = 0;

        this.timer = new Thread(() -> {

            while ( true ) {

                try { Thread.sleep(50); }
                catch (InterruptedException e) { log.error("cant sleep :(", e); }

                if (!this.inMaze ) { continue; }

                this.lcd.writeTextAtLine("Current Time:", 1);
                this.lcd.writeTextAtLine(String.format("%d seconds", this.timeInMaze), 2);
                this.timeInMaze++;
                this.log.info("Mouse in Maze");

                try { Thread.sleep(950); }
                catch (InterruptedException e) { log.error("cant sleep :(", e); }
            }
        });
        this.timer.start();

    }

    /*
        For the Lab automation, we are going to assume the mouse maze.
        The idea of this maze is to figure out how much time the mouse takes to exit the maze.
        We are going to use 2 ultrasonic sensors at the entry and exit locations.
        - Start and end time need to be noted based on the ultrasonic sensor's input( should have a threshold of 4 CM).
        - Subtract end time with the start time to get the time taken by the mouse to exit.
        - Use LCD to display messages like Mouse entered, mouse left and total time
     */

    @Get("/enable")
    public void enable() {
        this.pirStart.addEventListener((event) -> {

            if ( this.pirStart.isMoving  && !this.inMaze ) {
                this.timeInMaze = 0;
                this.inMaze = true;
                this.rgb.setGreen(255);
                this.rgb.setRed(0);
                this.startTime = System.currentTimeMillis();
                this.lcd.clearDisplay();
            }

        });

        this.pirEnd.addEventListener((event) -> {

            if ( this.pirEnd.isMoving && this.inMaze ) {
                this.inMaze = false;
                this.rgb.setGreen(0);
                this.rgb.setRed(255);
                this.endTime = System.currentTimeMillis();
                long finishTime = ( this.endTime - this.startTime ) / 1000;
                this.lcd.writeTextAtLine("Final Time:", 1);
                this.lcd.writeTextAtLine(String.format("%d seconds", finishTime), 2);
            }
        });
    }
}