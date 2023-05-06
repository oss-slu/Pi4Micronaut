package com.opensourcewithslu.utilities;

import com.opensourcewithslu.utilities.MultiPinConfigs.DigitalInputMultiPinConfiguration;
import com.opensourcewithslu.utilities.MultiPinConfigs.PwmMultiPinConfiguration;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.pwm.Pwm;
import io.micronaut.context.annotation.EachBean;
import io.micronaut.context.annotation.Factory;

@Factory
public class Pi4JMultipinFactory {

    //tag::digitalInput[]
    @EachBean(DigitalInputMultiPinConfiguration.class)                                                          //<.>
    public MultipinConfiguration multiPinInput(DigitalInputMultiPinConfiguration config, Context pi4jContext){  //<.>
        int[] addresses = config.getAddresses();                                                                //<.>
        DigitalInput[] allInputs = new DigitalInput[addresses.length];                                          //<.>

        for(int i = 0; i < addresses.length; i++){                                                              //<.>
            var inputConfigBuilder = DigitalInput.newConfigBuilder(pi4jContext)             //<.>
                    .id(config.getId() + String.valueOf(i))                                                     //<.>
                    .name(config.getName())
                    .address(config.getAddresses()[i])
                    .debounce(config.getDebounces()[i])
                    .pull(config.getPulls()[i])
                    .provider(config.getProvider());

            allInputs[i] = pi4jContext.create(inputConfigBuilder);                                              //<.>
        }

        return new MultipinConfiguration(config.getId(), allInputs);                                            //<.>
    }
    //end::digitalInput[]

    @EachBean(PwmMultiPinConfiguration.class)
    public MultipinConfiguration multiPinPwm(PwmMultiPinConfiguration config, Context pi4jContext){
        int[] addresses = config.getAddresses();
        Pwm[] allPwms = new Pwm[addresses.length];

        for(int i = 0; i < addresses.length; i++){
            var pwmConfigBuilder = Pwm.newConfigBuilder(pi4jContext)
                    .id(config.getId() + String.valueOf(i))
                    .name(config.getName())
                    .address(config.getAddresses()[i])
                    .pwmType(config.getPwmTypes()[i])
                    .initial(config.getInitals()[i])
                    .shutdown(config.getShutdowns()[i])
                    .provider(config.getProvider());

            allPwms[i] = pi4jContext.create(pwmConfigBuilder);
        }

        String multiPinId = config.getId().substring(0, config.getId().length() - 8);
        return new MultipinConfiguration(multiPinId, allPwms);
    }
}
