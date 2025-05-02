package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeEvent;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TouchSwitchHelperTest {

    @Mock
    private DigitalInput mockInput;

    @Captor
    private ArgumentCaptor<DigitalStateChangeListener> listenerCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInitialStateReflectsHighInput() {
        when(mockInput.isHigh()).thenReturn(true);
        TouchSwitchHelper helper = new TouchSwitchHelper(mockInput);

        assertTrue(helper.isTouched, "isTouched should be true when input is high");
        verify(mockInput).addListener(any(DigitalStateChangeListener.class));
    }

    @Test
    void testInitialStateReflectsLowInput() {
        when(mockInput.isHigh()).thenReturn(false);
        TouchSwitchHelper helper = new TouchSwitchHelper(mockInput);

        assertFalse(helper.isTouched, "isTouched should be false when input is low");
        verify(mockInput).addListener(any(DigitalStateChangeListener.class));
    }

    @Test
    void testDefaultListenerUpdatesIsTouched() {
        when(mockInput.isHigh()).thenReturn(false);
        TouchSwitchHelper helper = new TouchSwitchHelper(mockInput);

        verify(mockInput).addListener(listenerCaptor.capture());
        DigitalStateChangeListener internalListener = listenerCaptor.getValue();

        when(mockInput.isHigh()).thenReturn(true);
        internalListener.onDigitalStateChange(mock(DigitalStateChangeEvent.class));
        assertTrue(helper.isTouched, "Listener should set isTouched to true when input goes high");

        when(mockInput.isHigh()).thenReturn(false);
        internalListener.onDigitalStateChange(mock(DigitalStateChangeEvent.class));
        assertFalse(helper.isTouched, "Listener should set isTouched to false when input goes low");
    }

    @Test
    void testAddCustomListener() {
        when(mockInput.isHigh()).thenReturn(false);
        TouchSwitchHelper helper = new TouchSwitchHelper(mockInput);

        DigitalStateChangeListener customListener = mock(DigitalStateChangeListener.class);
        helper.addEventListener(customListener);

        verify(mockInput, times(2)).addListener(any(DigitalStateChangeListener.class));
        verify(mockInput).addListener(customListener);
    }

    @Test
    void testRemoveDefaultListener() {
        when(mockInput.isHigh()).thenReturn(false);
        TouchSwitchHelper helper = new TouchSwitchHelper(mockInput);

        verify(mockInput).addListener(listenerCaptor.capture());
        DigitalStateChangeListener defaultListener = listenerCaptor.getValue();

        reset(mockInput);

        helper.removeEventListener();
        verify(mockInput).removeListener(defaultListener);

        helper.removeEventListener();
        verifyNoMoreInteractions(mockInput);
    }

    @Test
    void testRemoveListenerIdempotent() {
        when(mockInput.isHigh()).thenReturn(false);
        TouchSwitchHelper helper = new TouchSwitchHelper(mockInput);

        helper.removeEventListener();
        assertDoesNotThrow(helper::removeEventListener, "removeEventListener should be idempotent");
    }

    @Test
    void testInitializationLogs() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(baos));
        try {
            when(mockInput.isHigh()).thenReturn(false);
            new TouchSwitchHelper(mockInput);
            System.out.flush();
            String output = baos.toString();
            assertTrue(output.contains("Initializing Touch Switch"),
                    "Expected log message 'Initializing Touch Switch' was not found in System.out output.");
        } finally {
            System.setOut(originalOut);
        }
    }
}
