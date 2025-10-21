package com.opensourcewithslu.inputdevices;

import static org.junit.jupiter.api.Assertions.*;

import com.opensourcewithslu.mock.MockDigitalInput;
import com.pi4j.io.gpio.digital.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PushButtonHelperTests {

    @org.junit.jupiter.api.Test
    /* This test simulates the push button in a LOW state without connecting to the actual hardware.
    It ensures that when the button input is LOW, the 'isPressed' state is correctly identified as false.
     */
    void initializeStateLow() {
        Logger log = LoggerFactory.getLogger(PushButtonHelper.class); //
        MockDigitalInput input = new MockDigitalInput(); // Mock input for the button
        input.SetState(DigitalState.LOW); // Simulate button in the LOW state
        PushButtonHelper helper = new PushButtonHelper(input); //
        assertFalse(helper.isPressed); // Button is not pressed when input is LOW
    }

    @org.junit.jupiter.api.Test
    /*
    This test simulates the push button in a HIGH state.
    It verifies that when the button input is HIGH, the 'IsPressed' state is correctly identified as true.
     */
    void initializeStateHigh() {
        MockDigitalInput input = new MockDigitalInput();
        input.SetState(DigitalState.HIGH); // Simulate button in the HIGH state
        PushButtonHelper helper = new PushButtonHelper(input);
        assertTrue(helper.isPressed); // Button is pressed when input is HIGH
    }

    @org.junit.jupiter.api.Test
    /*
        Placeholder for testing even listener functionality.
         */
    void addEventListener() {

    }
}