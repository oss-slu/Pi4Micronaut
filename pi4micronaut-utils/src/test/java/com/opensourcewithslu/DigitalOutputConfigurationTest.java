package com.opensourcewithslu;

import com.opensourcewithslu.utilities.DigitalOutputConfiguration;
import com.pi4j.io.gpio.digital.DigitalState;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.PropertySource;
import io.micronaut.inject.qualifiers.Qualifiers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test class to verify the configuration binding of DigitalOutputConfiguration.
 */
public class DigitalOutputConfigurationTest {

    @Test
    public void testDigitalOutputConfigurationBindingForLed() {
        // Create an in-memory property source that simulates the YAML configuration for the "led" digital output.
        ApplicationContext ctx = ApplicationContext.run(
                PropertySource.of("test",
                        "pi4j.digital-output.led.name", "LED Output",
                        "pi4j.digital-output.led.address", "17",
                        "pi4j.digital-output.led.shutdown", "LOW",
                        "pi4j.digital-output.led.initial", "LOW",
                        "pi4j.digital-output.led.provider", "pigpio-digital-output"
                )
        );

        // Retrieve the bean corresponding to the configuration entry with key "led"
        DigitalOutputConfiguration config = ctx.getBean(
                DigitalOutputConfiguration.class,
                Qualifiers.byName("led")
        );


        // Verify that each property has been bound correctly.
        Assertions.assertEquals("led", config.getId(), "Expected bean id to be 'led'");
        Assertions.assertEquals("LED Output", config.getName(), "Expected name to be 'LED Output'");
        Assertions.assertEquals(17, config.getAddress(), "Expected address to be 17");
        Assertions.assertEquals(DigitalState.LOW, config.getShutdown(), "Expected shutdown state to be LOW");
        Assertions.assertEquals(DigitalState.LOW, config.getInitial(), "Expected initial state to be LOW");
        Assertions.assertEquals("pigpio-digital-output", config.getProvider(), "Expected provider to be 'pigpio-digital-output'");

        ctx.close();
    }
}