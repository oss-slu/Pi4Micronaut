package com.opensourcewithslu.utilities;

import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;
import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.context.annotation.Prototype;

import java.util.Arrays;
import java.util.List;

public class MultipinConfiguration {
    private final String id;
    private final Object[] components;

    public MultipinConfiguration(String id, Object[] components){
        this.id = id;
        this.components = components;
    }

    public Object[] getComponents() {
        return components;
    }
}
