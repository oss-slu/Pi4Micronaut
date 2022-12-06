package com.opensourcewithslu.outputdevices;

import com.pi4j.io.pwm.Pwm;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class ServoMotorHelper {
    private static final Logger log = LoggerFactory.getLogger(LEDHelper.class);

    private Pwm servoPwm;

    public ServoMotorHelper(@Named("servo") Pwm servoPwm){
        this.servoPwm = servoPwm;
    }

    @PostConstruct
    public void initilize(){
        log.info("Initializing Servo Motor");
        servoPwm.on(30);
    }

    public void setAngle(int angle){
        log.info("Setting angle to: " + angle);
        servoPwm.on(angle);
    }
}
