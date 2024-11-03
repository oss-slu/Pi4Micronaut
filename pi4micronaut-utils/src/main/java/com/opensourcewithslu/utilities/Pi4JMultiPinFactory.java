package com.opensourcewithslu.utilities;

import com.opensourcewithslu.utilities.MultiPinConfigs.DigitalInputMultiPinConfiguration;
import com.opensourcewithslu.utilities.MultiPinConfigs.PwmMultiPinConfiguration;
import com.opensourcewithslu.utilities.MultiPinConfigs.DigitalOutputMultiPinConfiguration;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.pwm.Pwm;
import io.micronaut.context.annotation.EachBean;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;

/**
 * The Pi4JMultiPinFactory class is responsible for creating all the beans for all multi pin components that are being used.
 */
@Singleton
@Factory
public class Pi4JMultiPinFactory {

    /**
     * Default constructor for Pi4JMultiPinFactory.
     */
    public Pi4JMultiPinFactory() { }


    /**
     * Creates a MultiPinConfiguration object for a multi pin digital input component.
     * @param config {@link DigitalInputMultiPinConfiguration} Object.
     * @param pi4jContext The Pi4J {@link Context}.
     * @return A MultiPinConfiguration object.
     */
    @Singleton
    @EachBean(DigitalInputMultiPinConfiguration.class)
    public MultiPinConfiguration multiPinInput(DigitalInputMultiPinConfiguration config, Context pi4jContext){
        int[] addresses = config.getAddresses();
        DigitalInput[] allInputs = new DigitalInput[addresses.length];

        for(int i = 0; i < addresses.length; i++){
            var inputConfigBuilder = DigitalInput.newConfigBuilder(pi4jContext)
                    .id(config.getId() + i)
                    .name(config.getName())
                    .address(config.getAddresses()[i])
                    .debounce(config.getDebounces()[i])
                    .pull(config.getPulls()[i])
                    .provider(config.getProvider());

            allInputs[i] = pi4jContext.create(inputConfigBuilder);
        }

        return new MultiPinConfiguration(config.getId(), allInputs);
    }

    /**
     * Creates a MultiPinConfiguration object for a multi pin pwm component.
     * @param config {@link PwmMultiPinConfiguration} Object.
     * @param pi4jContext The Pi4J {@link Context}.
     * @return A MultiPinConfiguration object.
     */
    @Singleton
    @EachBean(PwmMultiPinConfiguration.class)
    public MultiPinConfiguration multiPinPwm(PwmMultiPinConfiguration config, Context pi4jContext){
        int[] addresses = config.getAddresses();
        Pwm[] allPWMs = new Pwm[addresses.length];

        for(int i = 0; i < addresses.length; i++){
            var pwmConfigBuilder = Pwm.newConfigBuilder(pi4jContext)
                    .id(config.getId() + i)
                    .name(config.getName())
                    .address(config.getAddresses()[i])
                    .pwmType(config.getPwmTypes()[i])
                    .initial(config.getInitials()[i])
                    .shutdown(config.getShutdowns()[i])
                    .provider(config.getProvider());

            allPWMs[i] = pi4jContext.create(pwmConfigBuilder);
        }

        String multiPinId = config.getId().substring(0, config.getId().length() - 8);
        return new MultiPinConfiguration(multiPinId, allPWMs);
    }


    /**
     * Creates a MultiPinConfiguration object for a multi pin digital output component.
     *
     * @param config      {@link DigitalOutputMultiPinConfiguration} Object.
     * @param pi4jContext The Pi4J {@link Context}.
     * @return A MultiPinConfiguration object.
     */
    @Singleton
    @EachBean(DigitalOutputMultiPinConfiguration.class)
    public MultiPinConfiguration multiPinOutput(DigitalOutputMultiPinConfiguration config, Context pi4jContext) {
        int[] addresses = config.getAddresses();
        DigitalOutput[] allOutputs = new DigitalOutput[addresses.length];

        for (int i = 0; i < addresses.length; i++) {
            var outputConfigBuilder = DigitalOutput.newConfigBuilder(pi4jContext)
                    .id(config.getId() + i)
                    .name(config.getName())
                    .address(config.getAddresses()[i])
                    .initial(config.getInitials()[i])
                    .shutdown(config.getShutdowns()[i])
                    .provider(config.getProvider());

            allOutputs[i] = pi4jContext.create(outputConfigBuilder);
        }

        return new MultiPinConfiguration(config.getId(), allOutputs);
    }
}
