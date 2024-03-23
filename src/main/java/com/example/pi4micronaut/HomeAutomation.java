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

    public HomeAutomation(
            @Named("lcd") I2CConfig lcd,
            @Named("rfid") SpiConfig rfid,
            @Named("led") DigitalOutput led,
            @Named("pir-sensor")DigitalInput pirSensor,
            @Named("active-buzzer") Pwm activeBuzzer,
            @Named("touch-switch") DigitalInput touchSwitch,
            @Named("ultra-sonic-trig") DigitalOutput trig,
            @Named("ultra-sonic-echo") DigitalInput echo,
            Context pi4j)
    {
        this.lcd = new LCD1602Helper(lcd,pi4j);
        this.rfid = new RFidHelper(rfid,pi4j);
        this.led = new LEDHelper(led);
        this.pirSensor = new PIRSensorHelper(pirSensor);
        this.touchSwitch = new TouchSwitchHelper(touchSwitch);
        this.activeBuzzer = new ActiveBuzzerHelper(activeBuzzer);
        this.ultraSonicSensor = new UltraSonicSensorHelper(trig,echo);
        this.users = new ArrayList<String>();
        this.isSystemOn = false;
        this.alarmOn = false;
        this.authorized = false;

        /* Thread for UltraSonic Sensor
         * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
         * Handles logic for components after the home automation system is turned on
         */
        this.threadUltraSonicSensor = new Thread(() -> {
            while (true) {
                try {
                    // Checks if active buzzer is on (if someone is within 1m of sensor)
                    if ( alarmOn ) {
                        // Read data from RFID scanner
                        String data = this.rfidHelper.readFromCard().toString();

                        // If authorized user, then turn off alarm welcome the user, and
                        // allow for the system to be turned off from touch sensor
                        if ( this.users.contains(data) ) {
                            this.alarmOn = false;
                            this.authorized = true;
                            this.lcd.writeText("Hello " + data);
                            this.led.ledOff();
                            this.activeBuzzer.activeBuzzerOff();
                        }
                    // Check if someone is within 1m of sensor and turn on alarm if true
                    } else if ( ultraSonicSensor.getDistanceInMeters() < 1 ) {
                        this.alarmOn = true;
                        this.activeBuzzer.activeBuzzerOn();
                    }

                    // Run every 500ms
                    Thread.sleep(500);

                } catch (InterruptedException e) {
                    log.error("Ultrasonic sensor measurement interrupted", e);
                    Thread.stop();
                }
            }

        });
    }

    // Enables the home automation system.
    @Get("/enable")
    public void enableHomeAutomation() {
        // Add listener to touch switch to start/stop home automation system
        this.touchSwitch.addEventListener((e) -> {
            if (this.touchSwitch.isTouched) {
                // If the system is on and an authorized user touches the sensor, shut off the system
                if (this.isSystemOn) {
                    if (this.authorized) {
                        this.isSystemOn = false;
                        this.pirSensor.removeEventListener();
                        this.threadUltraSonicSensor.stop();
                    }
                // If system is off, turn on system
                } else {
                    this.isSystemOn = true;
                    this.authorized = false;
                    // Adds listener for pir sensor
                    this.pirSensor.addEventListener((e) -> {
                        // If sensor senses motion, turn on led and ultrasonic sensor
                        if (this.pirSensor.isMoving) {
                            this.led.ledOn();
                            this.ultraSonicSensor.startMeasuring();
                            this.threadUltraSonicSensor.start();
                        // If sensor stops sensing motion, turn off led and ultrasonic sensor
                        } else {
                            this.led.ledOff();
                            this.ultraSonicSensor.stopMeasuring();
                            this.threadUltraSonicSensor.stop();
                        }
                    });
                    //this.threadUltraSonicSensor.start();
                }
            }
        });
    }

    // Adds new authorized user to home automation system
    @Post("/addUser/{value}")
    public void addUser(String value) {

        this.users.add(value);
        this.rfidHelper.writeToCard(value);
    }

    // Removes user from home automation system
    @Post("/removeUser/{value}")
    public void removeUser(String value) {
        this.users.remove(value);
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
