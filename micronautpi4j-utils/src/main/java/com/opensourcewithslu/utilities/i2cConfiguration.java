package com.opensourcewithslu.utilities;

import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;

@EachProperty("pi4j.i2c")
public class i2cConfiguration {

    private final String id;

    private String name;

    private int bus;

    private int device;

    public i2cConfiguration(@Parameter String id) {
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

    public int getBus() {
        return bus;
    }

    public void setBus(int bus) {
        this.bus = bus;
    }

    public int getDevice() {
        return device;
    }

    public void setDevice(int device) {
        this.device = device;
    }
}
