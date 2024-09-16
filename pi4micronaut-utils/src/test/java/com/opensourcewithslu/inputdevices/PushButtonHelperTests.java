package com.opensourcewithslu.inputdevices;

import static org.junit.jupiter.api.Assertions.*;
import com.pi4j.io.gpio.digital.*;

class PushButtonHelperTests {

    @org.junit.jupiter.api.Test
    void initializeStateLow() {
        MockDigitalInput input = new MockDigitalInput();
        input.SetState(DigitalState.LOW);
        PushButtonHelper helper = new PushButtonHelper(input);
        assertFalse(helper.isPressed);
    }

    @org.junit.jupiter.api.Test
    void initializeStateHigh() {
        MockDigitalInput input = new MockDigitalInput();
        input.SetState(DigitalState.HIGH);
        PushButtonHelper helper = new PushButtonHelper(input);
        assertTrue(helper.isPressed);
    }

    @org.junit.jupiter.api.Test
    void addEventListener() {
    }
}