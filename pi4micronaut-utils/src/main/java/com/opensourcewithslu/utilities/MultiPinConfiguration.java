package com.opensourcewithslu.utilities;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.context.annotation.Prototype;

/**
 * Class for configuring multiPin components.
 */
@Prototype
public class MultiPinConfiguration {
    private final String id;
    private final Object[] components;

    /**
     * The MultiPinConfiguration constructor.
     * @param id The configuration id as defined in the application.yml
     * @param components The array of components that are a part of the multiPin component.
     */
    public MultiPinConfiguration(@Parameter String id, Object[] components){
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
