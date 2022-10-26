package com.opensourcewithslu.components.utilities;

import com.pi4j.io.gpio.digital.DigitalState;
import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;

@EachProperty("pi4j.digital-output")
public class DigitalOutputConfiguration {

    private final String id;
    private String name;
    private DigitalState initial;
    private DigitalState shutdown;
    private int address;
    private String provider;

    public DigitalOutputConfiguration(@Parameter String id) {
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

    public DigitalState getInitial() {
        return initial;
    }

    public void setInitial(DigitalState initial) {
        this.initial = initial;
    }

    public DigitalState getShutdown() {
        return shutdown;
    }

    public void setShutdown(DigitalState shutdown) {
        this.shutdown = shutdown;
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
}
