package com.opensourcewithslu.inputdevices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import com.pi4j.io.gpio.digital.DigitalStateChangeEvent;



public class UltrasoundSensorHelper {

    private static final Logger log = LoggerFactory.getLogger(UltrasoundSensorHelper.class);

    private final DigitalInput ultraSoundInput;
    private final DigitalOutput ultraSoundOutput;

    private boolean sensorActive;
    private long startTime;
    private long endTime;

    private float distance;

    public UltrasoundSensorHelper(DigitalInput ultraSoundInput, DigitalOutput ultraSoundOutput) {
        this.ultraSoundInput = ultraSoundInput;
        this.ultraSoundOutput = ultraSoundOutput;
        this.ultraSoundOutput.high();
    }



    public void initialize()
    //end::method[]
    {
        log.info("Initializing " + ultraSoundInput.getName());
        this.sensorActive = true;

    }

    public void updateDistance() {}
}
