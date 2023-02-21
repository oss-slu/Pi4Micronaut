package com.opensourcewithslu.utilities;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmConfig;
import com.pi4j.io.spi.Spi;
import com.pi4j.io.spi.SpiConfig;
import io.micronaut.context.annotation.Bean;
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
    public DigitalOutput createDigitalOutput(DigitalOutputConfiguration config, Context pi4jContext) {
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
    public DigitalInput createDigitalInput(DigitalInputConfiguration config, Context pi4jContext) {
        var inputConfigBuilder = DigitalInput.newConfigBuilder(pi4jContext)
                .id(config.getId())
                .name(config.getName())
                .address(config.getAddress())
                .debounce(config.getDebounce())
                .pull(config.getPull())
                .provider(config.getProvider());
        return pi4jContext.create(inputConfigBuilder);
    }

    @EachBean(PwmConfiguration.class)
    public PwmConfig createPwm(PwmConfiguration config, Context pi4jContext) {
        var outputConfigBuilder = Pwm.newConfigBuilder(pi4jContext)
                .id(config.getId())
                .name(config.getName())
                .address(config.getAddress())
                .pwmType(config.getPwmType())
                .provider(config.getProvider())
                .initial(config.getInital())
                .shutdown(config.getShutdown())
                .build();
        return outputConfigBuilder;
    }

    @EachBean(SpiConfiguration.class)
    public SpiConfig createSpi(SpiConfiguration config, Context pi4jContext) {
        var outputConfigBuilder = Spi.newConfigBuilder(pi4jContext)
                .id(config.getId())
                .channel(config.getChannel())
                .mode(config.getMode())
                .baud(config.getBaud())
                .build();
        return outputConfigBuilder;
    }

    @EachBean(i2cConfiguration.class)
    public I2CConfig createI2C(i2cConfiguration config, Context pi4jContext) {
        var outputConfigBuilder = I2C.newConfigBuilder(pi4jContext)
                .id(config.getId())
                .name(config.getName())
                .bus(config.getBus())
                .device(config.getDevice())
                .build();
        return outputConfigBuilder;
    }
}
