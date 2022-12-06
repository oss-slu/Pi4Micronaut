package com.opensourcewithslu.utilities;

import com.pi4j.io.gpio.digital.DigitalState;
import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;

@EachProperty("pi4j.pwm")
public class PwmConfiguration {
    private final String id;
    private String name;
    private int initial;
    private int shutdown;
    private int frequency;
    private int address;
    private String provider;

    private int minDutyCycle;

    public int getMinDutyCycle() {
        return minDutyCycle;
    }

    public void setMinDutyCycle(int minDutyCycle) {
        this.minDutyCycle = minDutyCycle;
    }

    public int getMaxDutyCycle() {
        return maxDutyCycle;
    }

    public void setMaxDutyCycle(int maxDutyCycle) {
        this.maxDutyCycle = maxDutyCycle;
    }

    private int maxDutyCycle;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getInitial() {
        return initial;
    }

    public void setInitial(int initial) {
        this.initial = initial;
    }

    public Number getShutdown() {
        return shutdown;
    }

    public void setShutdown(int shutdown) {
        this.shutdown = shutdown;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public PwmConfiguration(@Parameter String id) {
        this.id = id;
    }
}
