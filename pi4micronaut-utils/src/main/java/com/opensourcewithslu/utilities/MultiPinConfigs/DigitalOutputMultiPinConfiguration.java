package com.opensourcewithslu.utilities.MultiPinConfigs;

import com.pi4j.io.gpio.digital.DigitalState;
import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.context.annotation.Prototype;

import java.util.Arrays;

/**
 * This class handles the configuration of a digital output component that has multiple pins.
 */
@Prototype
@EachProperty("pi4j.multi-digital-output")
public class DigitalOutputMultiPinConfiguration {
    private final String id;
    private String name;
    private int[] addresses;
    private DigitalState[] initials;
    private DigitalState[] shutdowns;
    private String provider;

    /**
     * The DigitalOutputMultiPinConfiguration constructor.
     *
     * @param id The configuration id as defined in the application.yml
     */
    public DigitalOutputMultiPinConfiguration(@Parameter String id) {
        this.id = id + "MultiPin";
    }

    /**
     * Gets the id of the component.
     *
     * @return The id of the component.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the name of the component.
     *
     * @return The name of the component.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the component.
     *
     * @param name The string name to replace the existing name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the pin addresses for the component.
     *
     * @return An array of the pin addresses.
     */
    public int[] getAddresses() {
        return addresses;
    }

    /**
     * Sets the pin addresses for the component. All previously existing address are replaced.
     *
     * @param addresses Pin addresses separated by a comma.
     */
    public void setAddresses(String addresses) {
        addresses = addresses.replaceAll("\\s", "");
        this.addresses = Arrays.stream(addresses.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    /**
     * Gets the initial states that the component is in when first initialized.
     *
     * @return Array of integers representing the initial state for each pin.
     */
    public DigitalState[] getInitials() {
        return initials;
    }

    /**
     * Sets the initial states for the component.
     *
     * @param initials Array of initial Digital states.
     */
    public void setInitials(DigitalState[] initials) {
        this.initials = initials;
    }

    /**
     * Gets the shutdown states for the component.
     *
     * @return Array of integers representing the shutdowns.
     */
    public DigitalState[] getShutdowns() {
        return shutdowns;
    }

    /**
     * Sets the shutdown states for the component. Existing shutdowns are replaced.
     *
     * @param shutdowns Array of shutdowns Digital states.
     */
    public void setShutdowns(DigitalState[] shutdowns) {
        this.shutdowns = shutdowns;
    }

    /**
     * Gets the provider for the component.
     *
     * @return A String representation of the provider.
     */
    public String getProvider() {
        return provider;
    }

    /**
     * Sets the provider.
     *
     * @param provider The new provider for the component.
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }
}
