package com.opensourcewithslu.utilities;

import com.pi4j.io.gpio.digital.DigitalState;
import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;

/**
 * This class handles the configuration of a digital output component.
 */
@EachProperty("pi4j.digital-output")
public class DigitalOutputConfiguration {

    private final String id;
    private String name;
    private DigitalState initial;
    private DigitalState shutdown;
    private int address;
    private String provider;

    /**
     *
     * @param id The configuration id as defined in the application.yml.
     */
    public DigitalOutputConfiguration(@Parameter String id) {
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
     * @return The String name of the component.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the component.
     * @param name The String that the name will be set as.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the initial state of the component.
     * @return The initial state of the component.
     */
    public DigitalState getInitial() {
        return initial;
    }

    /**
     * Sets the initial state for the component.
     * @param initial The Digital state that the component will start was.
     */
    public void setInitial(DigitalState initial) {
        this.initial = initial;
    }

    /**
     * Gets the shutdown state of the component.
     * @return The digital state of the component.
     */
    public DigitalState getShutdown() {
        return shutdown;
    }

    /**
     * Sets the shutdown state for the component.
     * @param shutdown DigitalState enum.
     */
    public void setShutdown(DigitalState shutdown) {
        this.shutdown = shutdown;
    }

    /**
     * Gets the pin address for the component.
     * @return An array of the pin addresses.
     */
    public int getAddress() {
        return address;
    }

    /**
     * Sets the pin address.
     * @param address Integer representing the new pin address.
     */
    public void setAddress(int address) {
        this.address = address;
    }

    /**
     * Gets the provider for the component.
     * @return A String representation of the provider.
     */
    public String getProvider() {
        return provider;
    }

    /**
     * Sets the provider.
     * @param provider The new provider for the component.
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }
}
