package com.opensourcewithslu.utilities;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.spi.Spi;
import com.pi4j.io.spi.SpiConfig;
import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalInputProvider;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalOutputProvider;
import com.pi4j.plugin.pigpio.provider.i2c.PiGpioI2CProvider;
import com.pi4j.plugin.pigpio.provider.pwm.PiGpioPwmProvider;
import com.pi4j.plugin.pigpio.provider.serial.PiGpioSerialProvider;
import com.pi4j.plugin.pigpio.provider.spi.PiGpioSpiProvider;
import com.pi4j.plugin.raspberrypi.platform.RaspberryPiPlatform;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.EachBean;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;

/**
 * The Pi4JFactory class is responsible for creating all the beans for components that are being used.
 */

@Factory
public class Pi4JFactory {
    /**
     * This creates the Pi4J Context that is used to create all the beans for the individual components.
     * @return A Pi4J Context
     */
    @Singleton
    @Bean(preDestroy = "shutdown")
    public com.pi4j.context.Context createPi4jContext() {
        final var piGpio = PiGpio.newNativeInstance();

        // Build Pi4J context with this platform and PiGPIO providers
        return Pi4J.newContextBuilder()
                .noAutoDetect()
                .add(
                        PiGpioDigitalInputProvider.newInstance(piGpio),
                        PiGpioDigitalOutputProvider.newInstance(piGpio),
                        PiGpioPwmProvider.newInstance(piGpio),
                        PiGpioI2CProvider.newInstance(piGpio),
                        PiGpioSerialProvider.newInstance(piGpio),
                        PiGpioSpiProvider.newInstance(piGpio)
                )
                .add(new RaspberryPiPlatform(){
                    @Override
                    protected String[] getProviders() {
                        return new String[]{};
                    }
                })
                .build();
    }

    /**
     * Creates a DigitalOutput object for digital output components.
     * @param config {@link DigitalOutputConfiguration} Object.
     * @param pi4jContext The Pi4J {@link Context}.
     * @return A DigitalOutput Object.
     */
    @Singleton
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

    /**
     * Creates a DigitalInput object for digital input components.
     * @param config {@link DigitalInputConfiguration} Object.
     * @param pi4jContext The Pi4J {@link Context}.
     * @return A DigitalInput Object.
     */
    @Singleton
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

    /**
     * Creates a PWM object for components that are pwm.
     * @param config {@link PwmConfiguration} Object.
     * @param pi4jContext The Pi4J {@link Context}.
     * @return A PWM Object.
     */
    @Singleton
    @EachBean(PwmConfiguration.class)
    public Pwm createPwm(PwmConfiguration config, Context pi4jContext) {

        return pi4jContext.create(
                Pwm.newConfigBuilder(pi4jContext)
                    .id(config.getId())
                    .name(config.getName())
                    .address(config.getAddress())
                    .pwmType(config.getPwmType())
                    .provider(config.getProvider())
                    .initial(config.getInital())
                    .shutdown(config.getShutdown())
                    .build()
            );
    }

    /**
     * Creates an SpiConfigBuilder object for components that are SPI.
     * @param config {@link SpiConfiguration} Object.
     * @param pi4jContext The Pi4J {@link Context}.
     * @return A SpiConfigBuilder Object.
     */
    @Singleton
    @EachBean(SpiConfiguration.class)
    public SpiConfig createSpi(SpiConfiguration config, Context pi4jContext) {
        return Spi.newConfigBuilder(pi4jContext)
                .id(config.getId())
                .name(config.getName())
                .address(config.getChannel())
                .baud(config.getBaud())
                .build();
    }

    /**
     * Creates an I2CConfigBuilder Object for components that are I2C.
     * @param config {@link i2cConfiguration} Object.
     * @param pi4jContext The Pi4J {@link Context}.
     * @return A I2CConfigBuilder Object.
     */
    @Singleton
    @EachBean(i2cConfiguration.class)
    public I2CConfig createI2C(i2cConfiguration config, Context pi4jContext) {
        return I2C.newConfigBuilder(pi4jContext)
                .id(config.getId())
                .name(config.getName())
                .bus(config.getBus())
                .device(config.getDevice())
                .build();
    }
}
