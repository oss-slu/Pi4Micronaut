package com.opensourcewithslu.outputdevices;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.Pi4J;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

@Singleton
public class SevenSegmentDisplayHelper {

    private final Context pi4j;
    private final DigitalOutput pinA;
    private final DigitalOutput pinB;
    private final DigitalOutput pinC;
    private final DigitalOutput pinD;
    private final DigitalOutput pinE;
    private final DigitalOutput pinF;
    private final DigitalOutput pinG;

    // Segment pattern configuration for displaying numbers 0-9
    private static final boolean[][] DIGIT_SEGMENTS = {
            {true, true, true, true, true, true, false}, // 0
            {false, true, true, false, false, false, false}, // 1
            {true, true, false, true, true, false, true}, // 2
            {true, true, true, true, false, false, true}, // 3
            {false, true, true, false, false, true, true}, // 4
            {true, false, true, true, false, true, true}, // 5
            {true, false, true, true, true, true, true}, // 6
            {true, true, true, false, false, false, false}, // 7
            {true, true, true, true, true, true, true}, // 8
            {true, true, true, true, false, true, true}  // 9
    };

    // Constructor that loads GPIO pin addresses from `application.yml`
    public SevenSegmentDisplayHelper(
            @Value("${pi4j.seven-segment-display.segment-pins.a}") int pinAAddress,
            @Value("${pi4j.seven-segment-display.segment-pins.b}") int pinBAddress,
            @Value("${pi4j.seven-segment-display.segment-pins.c}") int pinCAddress,
            @Value("${pi4j.seven-segment-display.segment-pins.d}") int pinDAddress,
            @Value("${pi4j.seven-segment-display.segment-pins.e}") int pinEAddress,
            @Value("${pi4j.seven-segment-display.segment-pins.f}") int pinFAddress,
            @Value("${pi4j.seven-segment-display.segment-pins.g}") int pinGAddress) {

        // Initialize Pi4J context
        this.pi4j = Pi4J.newAutoContext();

        if (pi4j == null) {
            throw new IllegalStateException("Pi4J context failed to initialize");
        }

        // Set up each pin based on addresses from configuration
        this.pinA = configurePin(pinAAddress, "PinA");
        this.pinB = configurePin(pinBAddress, "PinB");
        this.pinC = configurePin(pinCAddress, "PinC");
        this.pinD = configurePin(pinDAddress, "PinD");
        this.pinE = configurePin(pinEAddress, "PinE");
        this.pinF = configurePin(pinFAddress, "PinF");
        this.pinG = configurePin(pinGAddress, "PinG");
    }

    private DigitalOutput configurePin(int address, String id) {
        return pi4j.create(DigitalOutput.newConfigBuilder(pi4j)
                .id(id)
                .name(id)
                .address(address)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW));
    }

    public void display(int number) {
        if (number < 0 || number > 9) {
            throw new IllegalArgumentException("Number must be between 0 and 9");
        }
        setSegments(
                DIGIT_SEGMENTS[number][0],
                DIGIT_SEGMENTS[number][1],
                DIGIT_SEGMENTS[number][2],
                DIGIT_SEGMENTS[number][3],
                DIGIT_SEGMENTS[number][4],
                DIGIT_SEGMENTS[number][5],
                DIGIT_SEGMENTS[number][6]
        );
    }

    private void setSegments(boolean a, boolean b, boolean c, boolean d, boolean e, boolean f, boolean g) {
        pinA.state(a ? DigitalState.HIGH : DigitalState.LOW);
        pinB.state(b ? DigitalState.HIGH : DigitalState.LOW);
        pinC.state(c ? DigitalState.HIGH : DigitalState.LOW);
        pinD.state(d ? DigitalState.HIGH : DigitalState.LOW);
        pinE.state(e ? DigitalState.HIGH : DigitalState.LOW);
        pinF.state(f ? DigitalState.HIGH : DigitalState.LOW);
        pinG.state(g ? DigitalState.HIGH : DigitalState.LOW);
    }

    public void resetDisplay() {
        pinA.low();
        pinB.low();
        pinC.low();
        pinD.low();
        pinE.low();
        pinF.low();
        pinG.low();
    }

    public void shutdown() {
        pi4j.shutdown(); // showdown the program
    }
    //done!
}
