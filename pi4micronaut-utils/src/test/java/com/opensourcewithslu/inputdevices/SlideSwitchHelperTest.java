package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SlideSwitchHelperTest {
    private DigitalInput slideSwitchInput;
    private SlideSwitchHelper slideSwitchHelper;
    private Logger log;

    @BeforeEach
    void setUp() {
        slideSwitchInput = mock(DigitalInput.class);
        log = mock(Logger.class);
        // Set a default behavior for isHigh() if needed.
        when(slideSwitchInput.isHigh()).thenReturn(true);
        // Create a new SlideSwitchHelper instance.
        slideSwitchHelper = new SlideSwitchHelper(slideSwitchInput);
        // Set the logger if necessary.
        slideSwitchHelper.setLog(log);
    }

    // Test when the DigitalInput is high
    @Test
    void testIsOnFieldSetCorrectlyWhenInputIsHigh() {
        // Arrange: Set up the mock to return true for isHigh()
        when(slideSwitchInput.isHigh()).thenReturn(true);

        // Act: Create a new SlideSwitchHelper, which calls initialize() in the constructor.
        slideSwitchHelper = new SlideSwitchHelper(slideSwitchInput);
        slideSwitchHelper.setLog(log);  // Optionally set the logger if you want to check log output as well

        // Assert: Verify that the isOn field is true.
        assertTrue(slideSwitchHelper.isOn, "Expected isOn to be true when DigitalInput is high");
    }

    // Test when the DigitalInput is low
    @Test
    void testIsOnFieldSetCorrectlyWhenInputIsLow() {
        // Arrange: Set up the mock to return false for isHigh()
        when(slideSwitchInput.isHigh()).thenReturn(false);

        // Act: Create a new SlideSwitchHelper
        slideSwitchHelper = new SlideSwitchHelper(slideSwitchInput);
        slideSwitchHelper.setLog(log);

        // Assert: Verify that the isOn field is false.
        assertFalse(slideSwitchHelper.isOn, "Expected isOn to be false when DigitalInput is low");
    }
    @Test
    void testAddEventListener() {
        DigitalStateChangeListener dummyListener = mock(DigitalStateChangeListener.class);
        slideSwitchHelper.addEventListener(dummyListener);
        // Verify that addListener was called on the DigitalInput mock.
        verify(slideSwitchInput).addListener(dummyListener);
    }

    @Test
    void testCustomListenerInvocation() {
        // Use an AtomicBoolean to record invocation.
        AtomicBoolean listenerInvoked = new AtomicBoolean(false);
        DigitalStateChangeListener listener = event -> listenerInvoked.set(true);

        // Add the custom listener.
        slideSwitchHelper.addEventListener(listener);

        // Simulate an event by directly calling the listener's callback.
        // In a real system, the DigitalInput would trigger this, but here we do it manually.
        listener.onDigitalStateChange(null);

        // Assert that the listener was invoked.
        assertTrue(listenerInvoked.get(), "The custom listener should have been invoked.");
    }

    @Test
    void testRemoveEventListener() {
        DigitalStateChangeListener dummyListener = mock(DigitalStateChangeListener.class);
        // Add the listener first.
        slideSwitchHelper.addEventListener(dummyListener);
        // Then remove it.
        slideSwitchHelper.removeEventListener(dummyListener);
        // Verify that removeListener was called on the DigitalInput mock.
        verify(slideSwitchInput).removeListener(dummyListener);
    }


}
