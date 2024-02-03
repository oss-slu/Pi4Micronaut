package com.opensourcewithslu.utilities;

import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;
import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.context.annotation.Prototype;

import java.util.Arrays;
import java.util.List;

/**
 * Class for configuring multipin components.
 */
@Prototype
public class MultipinConfiguration {
    private final String id;
    private final Object[] components;

    /**
     *
     * @param id The configuration id as defined in the application.yml
     * @param components The array of components that are a part of the multipin component.
     */
    public MultipinConfiguration(String id, Object[] components){
        this.id = id;
        this.components = components;
    }

    /**
     * Gets the id of the component.
     * @return The id of the component.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the components that are part of the overall component.
     * @return an array of component objects.
     */
    public Object[] getComponents() {
        return components;
    }
}
