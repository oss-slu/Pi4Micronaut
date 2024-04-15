package com.example.pi4micronaut;

import com.opensourcewithslu.inputdevices.*;
import com.opensourcewithslu.outputdevices.*;

import com.opensourcewithslu.utilities.MultipinConfiguration;
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
    private final UltraSonicSensorHelper ultraSonicSensor;
    private Thread threadUltraSonicSensor;
    private ArrayList<String> users;
    private boolean isSystemOn;
    private boolean alarmOn;
    private boolean authorized;

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
        this.ultraSonicSensor = new UltraSonicSensorHelper(trig, echo);
        this.users = new ArrayList<String>();
        this.isSystemOn = false;
        this.alarmOn = false;
        this.authorized = false;
    }


    // Initializes Home Automation System.
    @Get("/init")
    public void initialize() {
        /* Thread for UltraSonic Sensor
         * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
         * Handles logic for components after the home automation system is turned on
         */
        this.threadUltraSonicSensor = new Thread(() -> {
            log.info("!! ULTRASONIC SENSOR THREAD ACTIVE !!");
            while (true) {

                //try {
                    // Run every 500ms
                    //Thread.sleep(500);

                    // Checks if active buzzer is on (if someone is within 1m of sensor)
                    if ( alarmOn ) {
                        // Read data from RFID scanner
                        String data = this.rfid.readFromCard().toString();

                        // If authorized user, then turn off alarm welcome the user, and
                        // allow for the system to be turned off from touch sensor
                        if ( this.users.contains(data) ) {
                            log.info("!! AUTHORIZED USER: TURN OFF BUZZER !!");
                            this.alarmOn = false;
                            this.authorized = true;
                            this.lcd.writeText("Hello " + data);
                            this.led.ledOn();
                            this.activeBuzzer.activeBuzzerOn();
                            this.threadUltraSonicSensor.interrupt();
                        }
                    // Check if someone is within 1m of sensor and turn on alarm if true
                    } else if ( ultraSonicSensor.getDistanceInCentimeter() < 100 ) {
                        log.info("!! PERSON DETECTED WITHIN 1M !!");
                        this.alarmOn = true;
                        this.activeBuzzer.activeBuzzerOff();
                    }
/*
                } catch (InterruptedException e) {
                    log.error("Ultrasonic sensor measurement interrupted", e);
                    Thread.currentThread().interrupt();
                }
*/
            }

        });

        log.info("!! HOME AUTOMATION SYSTEM INITIALIZED !!");

    }

    // Enables the home automation saystem.
    @Get("/enable")
    public String enableHomeAutomation() {
        // Add listener to touch switch to start/stop home automation system
        this.touchSwitch.addEventListener((e) -> {
            if (this.touchSwitch.isTouched) {
                // If the system is on and an authorized user touches the sensor, shut off the system
                if (this.isSystemOn) {
                    if (this.authorized) {
                        this.shutdown();
                    }
                    else {
                        log.info("!! SYSTEM STILL ON !!\nUNAUTHORIZED USER");
                    }
                // If system is off, turn on system
                } else {
                    log.info("!! SYSTEM ON !!\nTouch Sensor has been touched, Home Automation System turned ON (allegedly)");
                    this.isSystemOn = true;
                    this.authorized = false;
                    this.startup();
                }
            }
        });
       return "enabled";
    }

    // Adds new authorized user to home automation system
    @Get("/addUser/{value}")
    public void addUser(String value) {
        log.info("USER ADDED YAY! :D");
        this.users.add(value);
        this.rfid.writeToCard(value);
    }

    // Removes user from home automation system
    @Get("/removeUser/{value}")
    public void removeUser(String value) {
        log.info("USER REMOVED YAY! :D");
        this.users.remove(value);
    }

    // Shuts down Home Automation System
    private void shutdown() {
        log.info("!! SYSTEM OFF !!\nHome Automation System turned OFF (allegedly");
        this.isSystemOn = false;
        this.pirSensor.removeEventListener();
        this.threadUltraSonicSensor.interrupt();
    }

    // Starts up Home Automation System
    private void startup() {
        log.info("!! STARTING UP HOME AUTOMATION SYSTEM !!");
        this.led.ledOff();
        this.ultraSonicSensor.startMeasuring();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            log.error("cant sleep :(", e);
        }
        this.threadUltraSonicSensor.start();
        /*
        // Adds listener for pir sensor
        log.info("!! PIR SENSOR ACTIVE !!");
        this.pirSensor.addEventListener((event) -> {
            // If sensor senses motion, turn on led and ultrasonic sensor
            if (this.pirSensor.isMoving) {
                log.info("!! ULTRASONIC SENSOR ACTIVE !!");
                this.led.ledOff();
                this.ultraSonicSensor.startMeasuring();
                this.threadUltraSonicSensor.start();
                // If sensor stops sensing motion, turn off led and ultrasonic sensor
            } else if (!alarmOn) {
                log.info("!! ULTRASONIC SENSOR STOPPED (NO ONE NEARBY) !!");
                this.led.ledOn();
                this.ultraSonicSensor.stopMeasuring();
                this.threadUltraSonicSensor.interrupt();
            } else {

            }
        });

         */
    }


    /*
        1. Using the touch switch to activate and deactivate the Home_Automation
        2. Initially, PIR sensor will activate and detects the motion in the surroundings.
        3. Once motion is detected, turn on LED and start the UltraSonic sensor.
        4. Start displaying the UltraSonic Sensor readings in meters on LCD.
        4. If the person is close to 1 meter, ActiveBuzzer should start beeping.
        5. The only way to stop the buzzer is by scanning the RFID tag [Have a list of approved tags].
        6. After scanning the RFID and authorized, the buzzer should stop and display a message on LCD saying "Hello <RFID tag name>".
        7. On a successful scan of RFID, LED should turn off for indicating the authorization.
        8. Once authorized, user should be able to turn off the Home_Automation using touch switch.
     */

}
