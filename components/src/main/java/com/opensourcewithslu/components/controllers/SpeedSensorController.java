package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.SpeedSensorHelper;
import com.pi4j.io.gpio.digital.DigitalInput;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;

//tag::ex[]
@Controller("/speedSensor")
public class SpeedSensorController {

    private final SpeedSensorHelper speedSensorHelper;

    public SpeedSensorController(@Named("speed-sensor-pin") DigitalInput sensorPin, @Named("pulsesPerRevolution") double pulsesPerRevolution) {
        this.speedSensorHelper = new SpeedSensorHelper(sensorPin, pulsesPerRevolution);
    }

    /**
     * Enables the speed sensor and starts measuring speed.
     */
    @Get("/enable")
    public String enableSpeedSensor() {
        this.speedSensorHelper.startMeasuring();
        return "Speed Sensor Enabled";
    }

    /**
     * Returns the speed in RPM.
     */
    @Get("/speed/rpm")
    public String getSpeedInRPM() {
        return this.speedSensorHelper.getRPM() + " RPM\n";
    }

    /**
     * Disables the speed sensor.
     */
    @Get("/disable")
    public String disableSpeedSensor() {
        this.speedSensorHelper.stopMeasuring();
        return "Speed Sensor Disabled";
    }
}
//end::ex[]