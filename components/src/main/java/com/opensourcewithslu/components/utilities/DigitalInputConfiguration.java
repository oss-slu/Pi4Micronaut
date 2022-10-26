package com.opensourcewithslu.components.utilities;

import com.pi4j.io.gpio.digital.PullResistance;
import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;

@EachProperty("pi4j.digital-input")
public class DigitalInputConfiguration {

    private final String id;
    private String name;
    private Long debounce;
    private PullResistance pull;
    private int address;
    private String provider;

    public DigitalInputConfiguration(@Parameter String id) {
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

    public Long getDebounce() { return debounce; }

    public void setDebounce(Long debounce) { this.debounce = debounce; }

    public PullResistance getPull() { return pull; }

    public void setPull(PullResistance pull) { this.pull = pull; }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
