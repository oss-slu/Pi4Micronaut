package com.opensourcewithslu.utilities.MultiPinConfigs;

import com.pi4j.io.gpio.digital.PullResistance;
import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;

import java.util.Arrays;

/**
 * This class handles the configuration of a digital input component that has multiple pins.
 */
@EachProperty("pi4j.multi-digital-input")
public class DigitalInputMultiPinConfiguration {
    private final String id;
    private String name;
    private int[] addresses;
    private PullResistance[] pulls;
    private long[] debounces;
    private String provider;

    /**
     *
     * @param id The configuration id as defined in the application.yml
     */
    public DigitalInputMultiPinConfiguration(@Parameter String id){
        this.id = id + "Multipin";
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
     * Gets the pin addresses for the component.
     * @return An array of the pin addresses.
     */
    public int[] getAddresses() {
        return addresses;
    }

    /**
     * Sets the pin addresses for the component. All previously existing address are replaced.
     * @param addresses Pin addresses separated by a comma.
     */
    public void setAddresses(String addresses) {
        addresses = addresses.replaceAll("\\s", "");
        this.addresses = Arrays.stream(addresses.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    /**
     * Gets the pull resistance for the component.
     * @return The PullResistance enumerations.
     */
    public PullResistance[] getPulls() {
        return pulls;
    }

    /**
     * Sets all the pull resistance for the components.
     * @param all_pulls String of pull resistances separated by commas. Pull down resistance should be formatted as PULL_DOWN. Pull up as PULL_UP.
     */
    public void setPulls(String all_pulls) {
        all_pulls = all_pulls.replaceAll("\\s", "");
        String[] pulls = all_pulls.split(",");
        PullResistance[] pullResistances = new PullResistance[pulls.length];

        for(int i = 0; i < pulls.length; i++){
            if(pulls[i].trim().equals("PULL_DOWN")){
                pullResistances[i] = PullResistance.PULL_DOWN;
            }
            else{
                pullResistances[i] = PullResistance.PULL_UP;
            }
        }

        this.pulls = pullResistances;
    }

    /**
     * Gets the current debounce values for the component.
     * @return The array of debounce values of type long.
     */
    public long[] getDebounces() {
        return debounces;
    }

    /**
     * Sets the debounces for the component. Replaces all the existing debounces.
     * @param debounces String representing the dounces for the component. Each debounce seperated by a comma.
     */
    public void setDebounces(String debounces) {
        debounces = debounces.replaceAll("\\s", "");
        this.debounces = Arrays.stream(debounces.split(",")).mapToLong(Long::parseLong).toArray();
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
