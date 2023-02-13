package com.opensourcewithslu.utilities;

import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;
import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;

@EachProperty("pi4j.mulitpin")
public class MultipinConfiguration {
    private final String id;
    private String name;
    private int[] addresses;
    private long[] debounces;
    private PullResistance[] pulls;
    private DigitalState inital;
    private DigitalState shutdown;
    private String provider;

    public MultipinConfiguration(@Parameter String id) {
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

    public int[] getAddresses() {
        return addresses;
    }

    public void setAddresses(int[] addresses) {
        this.addresses = addresses;
    }

    public long[] getDebounces() {
        return debounces;
    }

    public void setDebounces(long[] debounces) {
        this.debounces = debounces;
    }

    public PullResistance[] getPulls() {
        return pulls;
    }

    public void setPulls(PullResistance[] pulls) {
        this.pulls = pulls;
    }

    public DigitalState getInital() {
        return inital;
    }

    public void setInital(DigitalState inital) {
        this.inital = inital;
    }

    public DigitalState getShutdown() {
        return shutdown;
    }

    public void setShutdown(DigitalState shutdown) {
        this.shutdown = shutdown;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
