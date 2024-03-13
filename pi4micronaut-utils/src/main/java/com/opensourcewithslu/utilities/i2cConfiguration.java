package com.opensourcewithslu.utilities;

import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.context.annotation.Prototype;

/**
 * This class handles the configuration of an I2C components.
 */
@Prototype
@EachProperty("pi4j.i2c")
public class i2cConfiguration {

    private final String id;

    private String name;

    private int bus;

    private int device;

    /**
     * The i2cConfiguration constructor.
     * @param id The configuration id as defined in the application.yml
     */
    public i2cConfiguration(@Parameter String id) {
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
     * Gets the bus pin for the component.
     * @return integer representing the bus.
     */
    public int getBus() {
        return bus;
    }

    /**
     * Sets the bus pin
     * @param bus integer representing the bus pin.
     */
    public void setBus(int bus) {
        this.bus = bus;
    }

    /**
     * Gets the device
     * @return the device represented by an integer.
     */
    public int getDevice() {
        return device;
    }

    /**
     * Sets the device
     * @param device The device as an integer.
     */
    public void setDevice(int device) {
        this.device = device;
    }
}
