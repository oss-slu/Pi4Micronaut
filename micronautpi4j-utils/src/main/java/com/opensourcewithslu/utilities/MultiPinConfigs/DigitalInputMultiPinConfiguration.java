package com.opensourcewithslu.utilities.MultiPinConfigs;

import com.pi4j.io.gpio.digital.PullResistance;
import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;

import java.util.Arrays;

@EachProperty("pi4j.multi-digital-input")
public class DigitalInputMultiPinConfiguration {
    private final String id;
    private String name;
    private int[] addresses;
    private PullResistance[] pulls;
    private long[] debounces;
    private String provider;

    public DigitalInputMultiPinConfiguration(@Parameter String id){
        this.id = id + "Multipin";
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

    public void setAddresses(String addresses) {
        this.addresses = Arrays.stream(addresses.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    public PullResistance[] getPulls() {
        return pulls;
    }

    public void setPulls(String all_pulls) {
        String[] pulls = all_pulls.split(",");
        PullResistance[] pullResistances = new PullResistance[pulls.length];

        for(int i = 0; i < pulls.length; i++){
            if(pulls[i].equals("PULL_DOWN")){
                pullResistances[i] = PullResistance.PULL_DOWN;
            }
            else{
                pullResistances[i] = PullResistance.PULL_UP;
            }
        }

        this.pulls = pullResistances;
    }

    public long[] getDebounces() {
        return debounces;
    }

    public void setDebounces(String debounces) {
        this.debounces = Arrays.stream(debounces.split(",")).mapToLong(Long::parseLong).toArray();
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
