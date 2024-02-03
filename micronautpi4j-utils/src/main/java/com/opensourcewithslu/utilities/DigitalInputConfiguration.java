package com.opensourcewithslu.utilities;

import com.pi4j.io.gpio.digital.PullResistance;
import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;

/**
 * This class handles the configuration of a digital input component.
 */
@EachProperty("pi4j.digital-input")
public class DigitalInputConfiguration {

    private final String id;
    private String name;
    private Long debounce;
    private PullResistance pull;
    private int address;
    private String provider;

    /**
     *
     * @param id The configuration id as defined in the application.yml
     */
    public DigitalInputConfiguration(@Parameter String id) {
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
     * Gets the current debounce value for the component.
     * @return Long type representing the debounce of the component.
     */
    public Long getDebounce() { return debounce; }

    /**
     * Sets the debounce of the component. Replaces existing debounce.
     * @param debounce New debounce of type Long,
     */
    public void setDebounce(Long debounce) { this.debounce = debounce; }

    /**
     * Gets the pull resistance for the component.
     * @return The PullResistance enumeration.
     */
    public PullResistance getPull() { return pull; }

    /**
     * Sets the pull resistance for the component.
     * @param pull The PullResistance enum that will become the new pull resistance for the component.
     */
    public void setPull(PullResistance pull) { this.pull = pull; }

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
