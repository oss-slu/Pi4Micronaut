package com.opensourcewithslu.outputdevices;

import com.pi4j.io.pwm.Pwm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServoMotorHelper {

    private static final Logger log = LoggerFactory.getLogger(ServoMotorHelper.class);

    private final Pwm servoMotor;

    public ServoMotorHelper(Pwm servoMotor)
    {
        this.servoMotor = servoMotor;
    }
}
