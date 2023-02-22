package com.opensourcewithslu.utilities;

import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;

@EachProperty("pi4j.softpwm")
public class SoftPwmConfiguration {
    private final String id;

    private int pin;

    private int value;

    private int range;

    public SoftPwmConfiguration(@Parameter String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

}
