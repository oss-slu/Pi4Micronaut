package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The JoystickHelper class enables reading input from an analog joystick
 * module.
 * It reads x-axis and y-axis values via an {@link ADC0834ConverterHelper}
 * (channels 0 and 1),
 * and detects button presses via a {@link DigitalInput} connected to the
 * joystick's SW pin.
 *
 * <p>
 * ADC value range: 0–255, where 128 represents the center position.
 * </p>
 * <p>
 * The SW pin is active LOW: the button is pressed when the digital input reads
 * LOW.
 * </p>
 */
public class JoystickHelper {

    private static final Logger log = LoggerFactory.getLogger(JoystickHelper.class);

    /** ADC channel used for the x-axis. */
    public static final int X_AXIS_CHANNEL = 0;

    /** ADC channel used for the y-axis. */
    public static final int Y_AXIS_CHANNEL = 1;

    /** ADC value representing the center (neutral) position. */
    public static final int CENTER_VALUE = 128;

    private final ADC0834ConverterHelper adcHelper;
    private final DigitalInput swInput;

    private DigitalStateChangeListener swListener;

    /**
     * Tracks whether the joystick button (SW) is currently pressed.
     * The SW pin is active LOW, so the button is pressed when the input is LOW.
     */
    public boolean isPressed;

    /**
     * JoystickHelper constructor.
     *
     * @param adcHelper The {@link ADC0834ConverterHelper} used to read analog axis
     *                  values.
     * @param swInput   The {@link DigitalInput} connected to the joystick's SW
     *                  (button) pin.
     */
    public JoystickHelper(ADC0834ConverterHelper adcHelper, DigitalInput swInput) {
        this.adcHelper = adcHelper;
        this.swInput = swInput;
        // SW is active LOW: button pressed = LOW signal
        this.isPressed = swInput.isLow();
        initialize();
    }

    // -------------------------------------------------------------------------
    // Initialization
    // -------------------------------------------------------------------------

    /**
     * Initializes the joystick by attaching a listener to the SW digital input.
     * Called automatically by the constructor.
     */
    public void initialize() {
        log.info("Initializing Joystick");
        swListener = e -> {
            isPressed = swInput.isLow();
            log.info("Joystick button state changed: isPressed={}", isPressed);
        };
        swInput.addListener(swListener);
        log.info("Joystick initialized successfully.");
    }

    // -------------------------------------------------------------------------
    // Axis Reads
    // -------------------------------------------------------------------------

    /**
     * Reads the current x-axis value from ADC channel 0.
     *
     * @return An integer in the range 0–255, where 128 is center.
     */
    public int getXValue() {
        int value = adcHelper.readValue(X_AXIS_CHANNEL);
        log.info("Joystick X-axis value: {}", value);
        return value;
    }

    /**
     * Reads the current y-axis value from ADC channel 1.
     *
     * @return An integer in the range 0–255, where 128 is center.
     */
    public int getYValue() {
        int value = adcHelper.readValue(Y_AXIS_CHANNEL);
        log.info("Joystick Y-axis value: {}", value);
        return value;
    }

    /**
     * Returns a human-readable direction based on the current x and y axis values.
     * Uses a deadzone of ±50 around center (128) to avoid noise.
     *
     * @return A string: "UP", "DOWN", "LEFT", "RIGHT", "CENTER", or a diagonal such
     *         as "UP-LEFT".
     */
    public String getDirection() {
        int x = getXValue();
        int y = getYValue();

        boolean left = x < CENTER_VALUE - 50;
        boolean right = x > CENTER_VALUE + 50;
        boolean up = y < CENTER_VALUE - 50;
        boolean down = y > CENTER_VALUE + 50;

        String direction;
        if (up && left)
            direction = "UP-LEFT";
        else if (up && right)
            direction = "UP-RIGHT";
        else if (down && left)
            direction = "DOWN-LEFT";
        else if (down && right)
            direction = "DOWN-RIGHT";
        else if (up)
            direction = "UP";
        else if (down)
            direction = "DOWN";
        else if (left)
            direction = "LEFT";
        else if (right)
            direction = "RIGHT";
        else
            direction = "CENTER";

        log.info("Joystick direction: {} (x={}, y={})", direction, x, y);
        return direction;
    }

    // -------------------------------------------------------------------------
    // Button
    // -------------------------------------------------------------------------

    /**
     * Returns whether the joystick button is currently pressed.
     * Reads directly from the digital input (active LOW).
     *
     * @return {@code true} if the button is pressed, {@code false} otherwise.
     */
    public boolean isButtonPressed() {
        isPressed = swInput.isLow();
        log.info("Joystick button isPressed={}", isPressed);
        return isPressed;
    }

    // -------------------------------------------------------------------------
    // Event Listener
    // -------------------------------------------------------------------------

    /**
     * Adds a custom event listener to the SW (button) digital input.
     *
     * @param listener A {@link DigitalStateChangeListener} to be notified on button
     *                 state changes.
     */
    public void addEventListener(DigitalStateChangeListener listener) {
        log.info("Adding event listener to joystick button.");
        swInput.addListener(listener);
    }

    /**
     * Removes the internal SW (button) event listener.
     */
    public void removeEventListener() {
        log.info("Removing event listener from joystick button.");
        if (swListener != null) {
            swInput.removeListener(swListener);
            swListener = null;
        }
    }
}