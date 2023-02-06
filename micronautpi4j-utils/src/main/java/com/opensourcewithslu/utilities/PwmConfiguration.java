package com.opensourcewithslu.utilities;

import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;

@EachProperty("pi4j.pwm")
public class PwmConfiguration {
    private final String id;

    private String name;

    private int address;

    private int frequency;

    private int inital;

    private int shutdown;

    public PwmConfiguration(@Parameter String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getInital() {
        return inital;
    }

    public void setInital(int inital) {
        this.inital = inital;
    }

    public int getShutdown() {
        return shutdown;
    }

    public void setShutdown(int shutdown) {
        this.shutdown = shutdown;
    }
}
