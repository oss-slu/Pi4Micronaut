package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TiltSwitchHelperTests {
    private DigitalInput mockDigitalInput;

    @BeforeEach
    void setUp() {
        mockDigitalInput = mock(DigitalInput.class);
        when(mockDigitalInput.isHigh()).thenReturn(false);
    }

    @Test
    public void initializeStateHigh() {
        when(mockDigitalInput.isHigh()).thenReturn(true);
        TiltSwitchHelper tiltSwitchHelper = new TiltSwitchHelper(mockDigitalInput);

        //Verify that isTilted is initialized to true
        assertTrue(tiltSwitchHelper.isTilted, "isTilted should be true when the input is high.");
        verify(mockDigitalInput, times(1)).addListener(any(DigitalStateChangeListener.class));
    }

    @Test
    public void initializeStateLow(){
        when(mockDigitalInput.isHigh()).thenReturn(false);
        TiltSwitchHelper tiltSwitchHelper = new TiltSwitchHelper(mockDigitalInput);

        //Verify that isTilted is initialized to false
        assertFalse(tiltSwitchHelper.isTilted, "isTilted should be false when the input is low.");
        verify(mockDigitalInput, times(1)).addListener(any(DigitalStateChangeListener.class));
    }

    @Test
    public void testEventListener(){
        when(mockDigitalInput.isHigh()).thenReturn(false);
        TiltSwitchHelper tiltSwitchHelper = new TiltSwitchHelper(mockDigitalInput);

        //Verify that custom listener is called
        final boolean[] customListenerInvoked = {false};
        DigitalStateChangeListener customerListener = event -> customListenerInvoked[0] = true;

        reset(mockDigitalInput);
        tiltSwitchHelper.addEventListener(customerListener);

        //Capture Listener
        ArgumentCaptor<DigitalStateChangeListener> captor = ArgumentCaptor.forClass(DigitalStateChangeListener.class);
        verify(mockDigitalInput).addListener(captor.capture());
        DigitalStateChangeListener capturedListener = captor.getValue();

        //State Change Event
        capturedListener.onDigitalStateChange(null);
        assertTrue(customListenerInvoked[0], "The custom event listener should have been invoked upon a state change event.");
    }

    @Test
    public void testRemoveEventListener() {
        when(mockDigitalInput.isHigh()).thenReturn(false);
        TiltSwitchHelper tiltSwitchHelper = new TiltSwitchHelper(mockDigitalInput);

        //Dummy Custom Listener
        DigitalStateChangeListener customListener = event -> { };

        reset(mockDigitalInput);

        //Add and Remove Custom Listener
        tiltSwitchHelper.addEventListener(customListener);
        tiltSwitchHelper.removeEventListener();

        //Verify removeListener was called
        verify(mockDigitalInput).removeListener(customListener);
    }

    @Test
    public void testStateUpdate() {
        when(mockDigitalInput.isHigh()).thenReturn(false);
        TiltSwitchHelper tiltSwitchHelper = new TiltSwitchHelper(mockDigitalInput);

        //Verify Initial State
        assertFalse(tiltSwitchHelper.isTilted, "Initially, isTilted should be false.");

        //Capture listener during initialization
        ArgumentCaptor<DigitalStateChangeListener> captor = ArgumentCaptor.forClass(DigitalStateChangeListener.class);
        verify(mockDigitalInput, times(1)).addListener(captor.capture());
        DigitalStateChangeListener registeredListener = captor.getValue();

        //State Change Event
        when(mockDigitalInput.isHigh()).thenReturn(true);
        registeredListener.onDigitalStateChange(null);
        assertTrue(tiltSwitchHelper.isTilted, "After state change, isTilted should be updated to true.");
    }

    @Test
    public void testRemoveEventListenerMultipleTimes() {
        TiltSwitchHelper tiltSwitchHelper = new TiltSwitchHelper(mockDigitalInput);

        //Custom Listener
        DigitalStateChangeListener customListener = event -> { };

        //Reset Interactions
        reset(mockDigitalInput);
        tiltSwitchHelper.addEventListener(customListener);

        //Removal of the EventListener; second removal should not throw an exception
        tiltSwitchHelper.removeEventListener();
        assertDoesNotThrow(() -> tiltSwitchHelper.removeEventListener(),
                "Calling removeEventListener() when no listener is attached should not throw an exception.");
    }

    @Test
    public void testStateChangeWithNoListenerAttached() {
        TiltSwitchHelper tiltSwitchHelper = new TiltSwitchHelper(mockDigitalInput);
        tiltSwitchHelper.removeEventListener();

        //Ensures proper handling of null listener
        assertDoesNotThrow(() -> {
            tiltSwitchHelper.removeEventListener();
        });
    }
}
