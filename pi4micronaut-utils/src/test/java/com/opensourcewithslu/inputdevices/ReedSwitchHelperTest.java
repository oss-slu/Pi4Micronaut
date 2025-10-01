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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ReedSwitchHelperTest {

    @Mock private DigitalInput mockInput;

    @Captor
    private ArgumentCaptor<DigitalStateChangeListener> listenerCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInitialStateReflectsHighInput() {
        when(mockInput.isHigh()).thenReturn(true);
        ReedSwitchHelper helper = new ReedSwitchHelper(mockInput);

        assertTrue(helper.isDetected, "isDetected should be true when input is high");
        verify(mockInput).addListener(any(DigitalStateChangeListener.class));
    }

    @Test
    void testInitialStateReflectsLowInput() {
        when(mockInput.isHigh()).thenReturn(false);
        ReedSwitchHelper helper = new ReedSwitchHelper(mockInput);

        assertFalse(helper.isDetected, "isDetected should be false when input is low");
        verify(mockInput).addListener(any(DigitalStateChangeListener.class));
    }

    @Test
    void testDefaultListenerUpdatesIsDetected() {
        when(mockInput.isHigh()).thenReturn(false);
        ReedSwitchHelper helper = new ReedSwitchHelper(mockInput);

        verify(mockInput).addListener(listenerCaptor.capture());
        DigitalStateChangeListener internalListener = listenerCaptor.getValue();

        when(mockInput.isHigh()).thenReturn(true);
        internalListener.onDigitalStateChange(mock(DigitalStateChangeEvent.class));
        assertTrue(helper.isDetected, "Listener should set isDetected to true when input goes high");

        when(mockInput.isHigh()).thenReturn(false);
        internalListener.onDigitalStateChange(mock(DigitalStateChangeEvent.class));
        assertFalse(helper.isDetected, "Listener should set isDetected to false when input goes low");
    }

    @Test
    void testAddCustomListener() {
        when(mockInput.isHigh()).thenReturn(false);
        ReedSwitchHelper helper = new ReedSwitchHelper(mockInput);

        DigitalStateChangeListener customListener = mock(DigitalStateChangeListener.class);
        helper.addEventListener(customListener);

        verify(mockInput, times(2)).addListener(any(DigitalStateChangeListener.class));
        verify(mockInput).addListener(customListener);
    }

    @Test
    void testRemoveEventListener() {
        when(mockInput.isHigh()).thenReturn(false);
        ReedSwitchHelper helper = new ReedSwitchHelper(mockInput);

        verify(mockInput).addListener(listenerCaptor.capture());
        DigitalStateChangeListener internalListener = listenerCaptor.getValue();

        helper.removeEventListener();
        verify(mockInput).removeListener(internalListener);

        helper.removeEventListener();
        verifyNoMoreInteractions(mockInput);

    }

    @Test
    void testRemoveListenerIdempotent() {
        when(mockInput.isHigh()).thenReturn(false);
        ReedSwitchHelper helper = new ReedSwitchHelper(mockInput);

        helper.removeEventListener();
        assertDoesNotThrow(helper::removeEventListener, "Removing listener multiple times should not throw");
    }


    
}
