package com.opensourcewithslu.utilities;

import com.pi4j.io.spi.SpiMode;
import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;

/**
 * This class handles the configuration of a SPI component.
 */
@EachProperty("pi4j.spi")
public class SpiConfiguration {
    private final String id;

    private String name;

    private int channel;

    private SpiMode mode;

    private int baud;

    /**
     *
     * @param id The configuration id as defined in the application.yml
     */
    public SpiConfiguration(@Parameter String id) {
        this.id = id;
    }

    /**
     * Gets the id of the component.
     * @return The id of the component.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the name of the component.
     * @return The name of the component.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the component.
     * @param name The string name to replace the existing name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the channel for the component.
     * @return An integer representing the channel.
     */
    public int getChannel() {
        return channel;
    }

    /**
     * Sets a new channel for the component.
     * @param channel An integer representing the new channel.
     */
    public void setChannel(int channel) {
        this.channel = channel;
    }

    /**
     * Gets the SPI mode for the component.
     * @return A SpiMode enum.
     */
    public SpiMode getMode() {
        return mode;
    }

    /**
     * Sets the SPI mode for the component.
     * @param mode An SpiMode object.
     */
    public void setMode(SpiMode mode) {
        this.mode = mode;
    }

    /**
     * Gets the baud rate for the component.
     * @return the baud rate as an integer.
     */
    public int getBaud() {
        return baud;
    }

    /**
     * Sets the baud rate for the component.
     * @param baud Integer representing the new baud rate.
     */
    public void setBaud(int baud) {
        this.baud = baud;
    }
}
