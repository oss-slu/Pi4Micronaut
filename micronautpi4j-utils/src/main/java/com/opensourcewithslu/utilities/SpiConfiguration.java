package com.opensourcewithslu.utilities;

import com.pi4j.io.spi.SpiMode;
import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;

@EachProperty("pi4j.spi")
public class SpiConfiguration {
    private final String id;

    private String name;

    private int channel;

    private SpiMode mode;

    private int baud;

    public SpiConfiguration(@Parameter String id) {
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

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public SpiMode getMode() {
        return mode;
    }

    public void setMode(SpiMode mode) {
        this.mode = mode;
    }

    public int getBaud() {
        return baud;
    }

    public void setBaud(int baud) {
        this.baud = baud;
    }
}
