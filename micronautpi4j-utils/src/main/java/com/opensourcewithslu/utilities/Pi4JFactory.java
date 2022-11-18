package com.opensourcewithslu.utilities;

import com.pi4j.Pi4J;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.EachBean;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;

@Factory
public class Pi4JFactory {
    @Singleton
    @Bean(preDestroy = "shutdown")
    public com.pi4j.context.Context createPi4jContext() {
        return Pi4J.newAutoContext();
    }

    @EachBean(DigitalOutputConfiguration.class)
    @Context
    public DigitalOutput createDigitalOutput(DigitalOutputConfiguration config, com.pi4j.context.Context pi4jContext) {
        var outputConfigBuilder = DigitalOutput.newConfigBuilder(pi4jContext)
                .id(config.getId())
                .name(config.getName())
                .address(config.getAddress())
                .shutdown(config.getShutdown())
                .initial(config.getInitial())
                .provider(config.getProvider());
        return pi4jContext.create(outputConfigBuilder);
    }

    @EachBean(DigitalInputConfiguration.class)
    public DigitalInput createDigitalInput(DigitalInputConfiguration config, com.pi4j.context.Context pi4jContext) {
        var inputConfigBuilder = DigitalInput.newConfigBuilder(pi4jContext)
                .id(config.getId())
                .name(config.getName())
                .address(config.getAddress())
                .debounce(config.getDebounce())
                .pull(config.getPull())
                .provider(config.getProvider());
        return pi4jContext.create(inputConfigBuilder);
    }
}
