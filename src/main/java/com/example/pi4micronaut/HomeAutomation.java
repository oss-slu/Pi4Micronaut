package com.example.pi4micronaut;

import com.opensourcewithslu.inputdevices.*;
import com.opensourcewithslu.outputdevices.*;

import com.opensourcewithslu.utilities.PwmConfiguration;
import com.pi4j.context.Context;
import com.pi4j.io.*;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.spi.SpiConfig;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.lang.Thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller("/HomeAutomation")
public class HomeAutomation {
    private final LCD1602Helper lcd;
    private final RFidHelper rfid;
    private final LEDHelper led;
    private final PIRSensorHelper pirSensor;
    private final TouchSwitchHelper touchSwitch;
    private final ActiveBuzzerHelper activeBuzzer;
    private UltraSonicSensorHelper ultraSonicSensor;
    private volatile Thread ultraSonicSensorThread;
    private Thread systemMonitorThread;
    private ArrayList<String> users;
    private boolean isSystemOn;
    private volatile boolean alarmOn;
    private volatile boolean authorized;
    private volatile boolean isThreadRunning;
    private volatile boolean isNearby;
    private volatile boolean isSonicActive;
    private DigitalOutput trig;
    private DigitalInput echo;

    private static final Logger log = LoggerFactory.getLogger(HomeAutomation.class);

    public HomeAutomation(
            @Named("lcd") I2CConfig lcd,
            @Named("rfid") SpiConfig rfid,
            @Named("led") DigitalOutput led,
            @Named("pir-sensor")DigitalInput pirSensor,
            @Named("active-buzzer") Pwm activeBuzzer,
            @Named("touch-switch") DigitalInput touchSwitch,
            @Named("ultra-sonic-trig") DigitalOutput trig,
            @Named("ultra-sonic-echo") DigitalInput echo,
            Context pi4j) {
        this.lcd = new LCD1602Helper(lcd, pi4j);

        this.rfid = new RFidHelper(rfid, 25, pi4j);
        this.led = new LEDHelper(led);
        this.pirSensor = new PIRSensorHelper(pirSensor);
        this.touchSwitch = new TouchSwitchHelper(touchSwitch);
        this.activeBuzzer = new ActiveBuzzerHelper(activeBuzzer);
        this.trig = trig;
        this.echo = echo;
        this.users = new ArrayList<String>();
        this.isSystemOn = false;
        this.alarmOn = false;
        this.authorized = false;
        this.isThreadRunning = false;
        this.isNearby = false;
        this.isSonicActive = false;

    }


    // Enables the home automation system.
    @Get("/enable")
    public String enableHomeAutomation() {

        // Add listener to touch switch to start/stop home automation system
        this.touchSwitch.addEventListener((e) -> {
            if (this.touchSwitch.isTouched) {
                // If the system is on and an authorized user touches the sensor, shut off the system
                if (this.isSystemOn) {
                    if (this.authorized) {
                        String data = this.rfid.readFromCard().toString();

                        // If authorized user, then turn off alarm welcome the user, and
                        // allow for the system to be turned off from touch sensor
                        if ( this.users.contains(data) ) {
                            this.lcd.writeText("SYSTEM OFF");
                            this.shutdown();
                        }
                    }
                    else {
                        this.lcd.writeText("UNAUTHORIZED USER");
                        log.info("!! UNAUTHORIZED USER !!");
                        try { Thread.sleep(3000); }
                        catch (InterruptedException f) { log.error("cant sleep :(", f); }
                        this.lcd.clearDisplay();

                    }
                // If system is off, turn on system
                } else if (!this.isSystemOn){
                    log.info("!! SYSTEM ON !!");
                    this.lcd.setBackLight(true);
                    this.lcd.writeText("SYSTEM ON");
                    this.isSystemOn = true;
                    this.authorized = false;
                    this.startup();
                }
            }
        });
        return "enabled";
    }


    // Adds new authorized user to home automation system
    @Get("/addUser/{userID}")
    public void addUser(String userID) {

        if ( !this.users.contains(userID) ) {
            this.rfid.writeToCard(userID);
            this.users.add(userID);
            log.info("!! USER ADDED !!");
        }
    }


    // Removes user from home automation system
    @Get("/removeUser/{userID}")
    public void removeUser(String userID) {
        this.users.remove(userID);
        log.info("!! USER REMOVED !!");
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * *\
    |* *                                               * *|
    |* *          Private  Helper  Functions           * *|
    |* *          --------------------------           * *|
    \* * * * * * * * * * * * * * * * * * * * * * * * * * */


    // Initializes Home Automation System.
    private void initialize() {

        this.led.ledOn();
        this.activeBuzzer.activeBuzzerOff();
        this.lcd.setBackLight(false);

        this.systemMonitorThread = new Thread(() -> {
            while (true) {

                if (this.isNearby && !this.isSonicActive) {
                    log.info("!! ULTRASONIC SENSOR ACTIVE !!");

                    this.led.ledOff();
                    this.isSonicActive = true;
                    this.isThreadRunning = true;
                    this.alarmOn = false;
                    this.buildUltraSonicThread();

                } else if (!this.isNearby) {
                    this.isThreadRunning = false;
                }

                try { Thread.sleep(200); }
                catch (InterruptedException e) { log.error("cant sleep :(", e); }

            }
        });

        this.systemMonitorThread.start();
    }


    // Builds thread for Ultra Sonic Sensor
    private void buildUltraSonicThread() {

        /* Thread for UltraSonic Sensor
         * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
         * Handles logic for components after the home automation system is turned on
         */
        this.ultraSonicSensorThread = new Thread(() -> {
            log.info("!! ULTRASONIC SENSOR THREAD ACTIVE !!");
            while (true) {

                if (!this.isThreadRunning) {
                    log.info("!! STOPPING ULTRASONIC THREAD !!");
                    this.isNearby = false;
                    this.isSonicActive = false;
                    this.ultraSonicSensor.stopMeasuring();
                    break;
                }

                // Checks if active buzzer is on (if someone is within 20 cm of sensor)
                if ( alarmOn ) {
                    // Read data from RFID scanner
                    String data = this.rfid.readFromCard().toString();

                    // If authorized user, then turn off alarm welcome the user, and
                    // allow for the system to be turned off from touch sensor
                    if ( this.users.contains(data) ) {
                        log.info("!! AUTHORIZED USER: TURN OFF BUZZER !!");
                        this.isThreadRunning = false;
                        this.alarmOn = false;
                        this.authorized = true;
                        this.lcd.writeText("Hello " + data);
                        this.led.ledOn();
                        this.activeBuzzer.activeBuzzerOff();
                    }
                // Check if someone is within 20 cm of sensor and turn on alarm if true
                } else if ( ultraSonicSensor.getDistanceInCentimeter() < 20 ) {
                    log.info("!! PERSON DETECTED WITHIN 20 CM !!");
                    this.alarmOn = true;
                    this.activeBuzzer.activeBuzzerOn();
                }
                try { Thread.sleep(100); }
                catch (InterruptedException e) { log.error("cant sleep :(", e); }
            }
        });

        log.info("!! HOME AUTOMATION SYSTEM INITIALIZED !!");

        this.ultraSonicSensor = new UltraSonicSensorHelper(trig, echo);
        this.ultraSonicSensor.startMeasuring();

        try { Thread.sleep(1000); }
        catch (InterruptedException e) { log.error("cant sleep :(", e); }

        this.ultraSonicSensorThread.start();
    }


    // Starts up Home Automation System
    private void startup() {

        log.info("!! STARTING UP HOME AUTOMATION SYSTEM !!");

        this.initialize();

        // Adds listener for pir sensor
        log.info("!! PIR SENSOR ACTIVE !!");
        this.pirSensor.addEventListener((event) -> {
            // If sensor senses motion, notify system that someone is nearby
            if ( this.isNearby ) {
                try { Thread.sleep(8000); }
                catch (InterruptedException e) { log.error("cant sleep :(", e); }
            }

            if ( this.pirSensor.isMoving ) {
                this.isNearby = true;

            // If no motion is sensed, notify system that nobody is nearby
            } else {
                if ( !alarmOn ) { this.led.ledOn(); }
                this.isNearby = false;
            }
        });
    }


    // Shuts down Home Automation System
    private void shutdown() {
        log.info("!! SYSTEM OFF !!");
        this.isSystemOn = false;
        this.pirSensor.removeEventListener();
        this.ultraSonicSensorThread.interrupt();
        try { Thread.sleep(3000); }
        catch (InterruptedException e) { log.error("cant sleep :(", e); }
        this.lcd.clearDisplay();
        this.lcd.setBackLight(false);
    }
}
