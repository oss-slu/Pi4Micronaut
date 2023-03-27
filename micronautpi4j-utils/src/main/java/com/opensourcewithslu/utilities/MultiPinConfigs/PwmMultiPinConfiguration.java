package com.opensourcewithslu.utilities.MultiPinConfigs;

import com.opensourcewithslu.utilities.Pi4JMultipinFactory;
import com.pi4j.io.gpio.digital.PullResistance;
import com.pi4j.io.pwm.PwmType;
import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@EachProperty("pi4j.multi-pwm")
public class PwmMultiPinConfiguration {
    private static final Logger log = LoggerFactory.getLogger(PwmMultiPinConfiguration.class);
    private final String id;
    private String name;
    private int[] addresses;
    private PwmType[] pwmTypes;
    private int[] initials;
    private int[] shutdowns;
    private String provider;

    public PwmMultiPinConfiguration(@Parameter String id){
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
        addresses = addresses.replaceAll("\\s", "");
        this.addresses = Arrays.stream(addresses.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    public PwmType[] getPwmTypes() {
        return pwmTypes;
    }

    public void setPwmTypes(String pwmTypes) {
        String[] pwms = pwmTypes.split(",");
        PwmType[] all_pwms = new PwmType[pwms.length];

        for(int i = 0; i < pwms.length; i++){
            if(pwms[i].trim().equals("SOFTWARE")){
                all_pwms[i] = PwmType.SOFTWARE;
            }
            else{
                all_pwms[i] = PwmType.HARDWARE;
            }
        }

        this.pwmTypes = all_pwms;
    }

    public int[] getInitals() {
        return initials;
    }

    public void setInitials(String initials) {
        initials = initials.replaceAll("\\s", "");
        this.initials = Arrays.stream(initials.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    public int[] getShutdowns() {
        return shutdowns;
    }

    public void setShutdowns(String shutdowns) {
        shutdowns = shutdowns.replaceAll("\\s", "");
        this.shutdowns = Arrays.stream(shutdowns.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
