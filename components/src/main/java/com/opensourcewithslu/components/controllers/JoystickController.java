package com.opensourcewithslu.components.controllers;

import com.opensourcewithslu.inputdevices.ADC0834ConverterHelper;
import com.opensourcewithslu.inputdevices.JoystickHelper;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.spi.SpiConfig;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The JoystickController class provides REST endpoints that demonstrate
 * how to use the {@link JoystickHelper} class to read joystick input.
 */
// tag::ex[]
@Controller("/joystick")
public class JoystickController {

    private static final Logger log = LoggerFactory.getLogger(JoystickController.class);

    private final JoystickHelper joystickHelper;

    /**
     * JoystickController constructor.
     *
     * @param spiConfig   The SPI configuration for the ADC0834 converter.
     * @param pi4jContext The Pi4J context.
     * @param swInput     The digital input connected to the joystick SW (button)
     *                    pin.
     */
    public JoystickController(@Named("joystick-adc") SpiConfig spiConfig,
            Context pi4jContext,
            @Named("joystick-sw") DigitalInput swInput) {
        ADC0834ConverterHelper adcHelper = new ADC0834ConverterHelper(spiConfig, pi4jContext);
        this.joystickHelper = new JoystickHelper(adcHelper, swInput);
    }

    /**
     * Returns the current x-axis value (0–255, center = 128).
     *
     * @return The x-axis ADC value.
     */
    @Get("/x")
    public int getXValue() {
        int value = joystickHelper.getXValue();
        log.info("X-axis value: {}", value);
        return value;
    }

    /**
     * Returns the current y-axis value (0–255, center = 128).
     *
     * @return The y-axis ADC value.
     */
    @Get("/y")
    public int getYValue() {
        int value = joystickHelper.getYValue();
        log.info("Y-axis value: {}", value);
        return value;
    }

    /**
     * Returns the current joystick direction as a string.
     * Possible values: UP, DOWN, LEFT, RIGHT, CENTER, UP-LEFT, UP-RIGHT, DOWN-LEFT,
     * DOWN-RIGHT.
     *
     * @return A direction string.
     */
    @Get("/direction")
    public String getDirection() {
        String direction = joystickHelper.getDirection();
        log.info("Direction: {}", direction);
        return direction;
    }

    /**
     * Returns whether the joystick button is currently pressed.
     *
     * @return {@code true} if pressed, {@code false} otherwise.
     */
    @Get("/isPressed")
    public boolean isPressed() {
        boolean pressed = joystickHelper.isButtonPressed();
        log.info("Button isPressed: {}", pressed);
        return pressed;
    }
}
// end::ex[]