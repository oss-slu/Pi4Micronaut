package com.opensourcewithslu.utilities;

import com.pi4j.io.pwm.PwmType;
import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.context.annotation.Prototype;

/**
 * The PwmConfiguration class handles the configuration of a pwm component.
 */
@Prototype
@EachProperty("pi4j.pwm")
public class PwmConfiguration {
    private final String id;
    private String name;
    private int address;
    private PwmType pwmType;
    private String provider;
    private int initial;
    private int shutdown;

    /**
     * PwmConfiguration constructor.
     * @param id The configuration id as defined in the application.yml
     */
    public PwmConfiguration(@Parameter String id) {
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
     * Gets the pin address for the component.
     * @return The address as an integer.
     */
    public int getAddress() {
        return address;
    }

    /**
     * Sets a new pin address for the component.
     * @param address An integer representing the new pin address.
     */
    public void setAddress(int address) {
        this.address = address;
    }

    /**
     * Gets the initial state that the component is in when first initialized.
     * @return The state as an integer.
     */
    public int getInitial() {
        return initial;
    }

    /**
     * Sets the initial state that the component will be in when first initialized.
     * @param initial The startup state as an integer.
     */
    public void setInitial(int initial) {
        this.initial = initial;
    }

    /**
     * Gets the shutdown state for the component.
     * @return The shutdown state as an integer.
     */
    public int getShutdown() {
        return shutdown;
    }

    /**
     * Sets the shutdown state for the component.
     * @param shutdown Integer representing the new shutdown state.
     */
    public void setShutdown(int shutdown) {
        this.shutdown = shutdown;
    }

    /**
     * Gets the pwm type of the component.
     * @return A PwmType object.
     */
    public PwmType getPwmType() {
        return pwmType;
    }

    /**
     * Sets the pwm type.
     * @param pwmType A string representing the new pwm type. Either SOFTWARE or HARDWARE.
     */
    public void setPwmType(String pwmType) {
        if(pwmType.equals("SOFTWARE")){
            this.pwmType = PwmType.SOFTWARE;
        } else if (pwmType.equals("HARDWARE")) {
            this.pwmType = PwmType.HARDWARE;
        }
    }

    /**
     * Gets the provider for the component.
     * @return The provider as a String.
     */
    public String getProvider() {
        return provider;
    }

    /**
     * Sets the provider of the component.
     * @param provider The new provider as a String.
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }
}
