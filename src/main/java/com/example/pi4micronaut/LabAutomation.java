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
    private long startTime;
    private long currentTime;
    private long endTime;

    private static final Logger log = LoggerFactory.getLogger(LabAutomation.class);

    // Lab Automation System detects when a mouse enters and leaves a maze and
    // displays the time it takes for the mouse to complete the maze in seconds.
    public LabAutomation(
            @Named("lcd") I2CConfig lcd,
            @Named("rgb-led") MultipinConfiguration rgb,
            @Named("pir-sensor-1") DigitalInput pirStart,
            @Named("pir-sensor-2") DigitalInput pirEnd,
            Context pi4j) {
        this.lcd = new LCD1602Helper(lcd, pi4j);
        this.rgb = new RGBLEDHelper(rgb);
        this.pirStart = new PIRSensorHelper(pirStart);
        this.pirEnd = new PIRSensorHelper(pirEnd);

        this.inMaze = false;

        // Timer thread: counts and displays how long the mouse has been in the maze in seconds
        this.timer = new Thread(() -> {

            while (true) {

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    log.error("cant sleep :(", e);
                }

                // If the mouse is not in the maze, dont execute the rest of the while block
                if (!this.inMaze) { continue; }

                // Calculate how long the mouse has been in the maze and write to LCD display
                long newTime = System.currentTimeMillis();
                if ( newTime - this.currentTime >= 1000 ) {
                    long displayTime = (newTime - this.startTime) / 1000;
                    this.currentTime = newTime;
                    this.lcd.writeTextAtLine("Current Time:", 1);
                    this.lcd.writeTextAtLine(String.format("%d seconds", displayTime), 2);
                }

            }
        });
        this.timer.start();

    }

    // Enables the Lab Automation system
    @Get("/enable")
    public void enable() {

        // Adds event listener for the motion sensor at the start of the maze
        this.pirStart.addEventListener((event) -> {

            // Detects if mouse has entering the maze
            if ( this.pirStart.isMoving  && !this.inMaze ) {
                this.inMaze = true;
                this.rgb.setGreen(255);
                this.rgb.setRed(0);
                this.startTime = System.currentTimeMillis();
                this.currentTime = this.startTime;
                this.lcd.clearDisplay();
            }

        });

        // Adds event listener for the motion sensor at the end of the maze
        this.pirEnd.addEventListener((event) -> {

            // Detects if mouse has exited the maze
            if ( this.pirEnd.isMoving && this.inMaze ) {
                this.inMaze = false;
                this.rgb.setGreen(0);
                this.rgb.setRed(255);
                this.endTime = System.currentTimeMillis();
                long finishTime = ( this.endTime - this.startTime ) / 1000;
                this.lcd.writeTextAtLine("Final Time:", 1);
                this.lcd.writeTextAtLine(String.format("%d seconds", finishTime), 2);
                this.log.info(String.format("Final Time: %d seconds", finishTime));
            }
        });
    }
}