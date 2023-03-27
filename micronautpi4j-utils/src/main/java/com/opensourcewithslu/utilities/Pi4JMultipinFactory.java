package com.opensourcewithslu.utilities;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import jakarta.inject.Inject;

public class Pi4JMultipinFactory {
    @Inject
    Context pi4jContext;
    public DigitalInput[] multiPinInput(MultipinConfiguration config){
        int[] addresses = config.getAddresses();

        DigitalInput[] allInputs = new DigitalInput[addresses.length];

        for(int i = 0; i < addresses.length; i++){
            var inputConfigBuilder = DigitalInput.newConfigBuilder(pi4jContext)
                    .id(config.getId())
                    .name(config.getName())
                    .address(config.getAddresses()[i])
                    .debounce(config.getDebounces()[i])
                    .pull(config.getPulls()[i])
                    .provider(config.getProvider());

            allInputs[i] = pi4jContext.create(inputConfigBuilder);
        }

        return allInputs;
    }
}
