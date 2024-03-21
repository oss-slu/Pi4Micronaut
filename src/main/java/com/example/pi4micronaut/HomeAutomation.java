package com.example.pi4micronaut;

import com.opensourcewithslu.inputdevices.*;
import com.opensourcewithslu.outputdevices.*;

import com.opensourcewithslu.utilities.MultiPinConfiguration;
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

@Controller("/HomeAutomation")
public class HomeAutomation {
    private final LCD1602Helper lcd;
    private final RFidHelper rfid;
    private final LEDHelper led;
    private final RGBLEDHelper rgb;
    private final PIRSensorHelper pirSensor;
    private final TouchSwitchHelper touchSwitch;
    private final ActiveBuzzerHelper activeBuzzer;
    private final UltraSonicSensorHelper ultraSonicSensor;

    public HomeAutomation(
            @Named("lcd") I2CConfig lcd,
            @Named("rfid") SpiConfig rfid,
            @Named("led") DigitalOutput led,
            @Named("rgb-led") MultiPinConfiguration rgb,
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
        this.rgb = new RGBLEDHelper(rgb);
        this.pirSensor = new PIRSensorHelper(pirSensor);
        this.touchSwitch = new TouchSwitchHelper(touchSwitch);
        this.activeBuzzer = new ActiveBuzzerHelper(activeBuzzer);
        this.ultraSonicSensor = new UltraSonicSensorHelper(trig,echo);
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
