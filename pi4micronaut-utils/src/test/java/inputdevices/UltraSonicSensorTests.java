package com.opensourcewithslu.inputdevices;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.LoggerFactory;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;

public class UltraSonicSensorTests {
    private DigitalInput mockDigitalInput;
    private UltraSonicSensor sensor;
    private static final Logger logger = LoggerFactory.getLogger(UltraSonicSensorTests.class);

    @BeforeEach
    void setUp() {
        mockDigitalInput = mock(DigitalInput.class);
        when(mockDigitalInput.isHigh()).thenReturn(false);
        sensor = new UltraSonicSensor(mockDigitalInput);
    }

    @Test
    void testInitialization() {
        assertNotNull(sensor);
        verify(mockDigitalInput).addListener(any(DigitalStateChangeListener.class));
    }

    @Test
    void testDistanceCalculation() {
        // Simulate echo pulse duration of 1000 microseconds (1ms)
        // Distance should be (1000 * 34300) / 2000000 = 0.01715 meters = 1.715 cm
        
        ArgumentCaptor<DigitalStateChangeListener> listenerCaptor = ArgumentCaptor.forClass(DigitalStateChangeListener.class);
        verify(mockDigitalInput).addListener(listenerCaptor.capture());
        
        DigitalStateChangeListener listener = listenerCaptor.getValue();
        
        // Simulate high state (start of echo)
        when(mockDigitalInput.isHigh()).thenReturn(true);
        listener.onDigitalStateChange(null); // Trigger the state change event
        
        // Wait 1ms
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            logger.error("Sleep interrupted", e);
        }
        
        // Simulate low state (end of echo)
        when(mockDigitalInput.isHigh()).thenReturn(false);
        listener.onDigitalStateChange(null); // Trigger the state change event
        
        // Allow for some tolerance in floating point comparison
        double expectedDistance = 1.715; // cm
        double distance = sensor.getDistance();
        
        assertTrue(Math.abs(distance - expectedDistance) < 0.5, 
                "Expected distance around " + expectedDistance + " cm but got " + distance + " cm");
    }
    
    @Test
    void testTimeout() {
        // Set up the sensor with a very short timeout
        sensor = new UltraSonicSensor(mockDigitalInput, 50); // 50ms timeout
        
        ArgumentCaptor<DigitalStateChangeListener> listenerCaptor = ArgumentCaptor.forClass(DigitalStateChangeListener.class);
        verify(mockDigitalInput, atLeast(1)).addListener(listenerCaptor.capture());
        
        DigitalStateChangeListener listener = listenerCaptor.getValue();
        
        // Simulate high state (start of echo)
        when(mockDigitalInput.isHigh()).thenReturn(true);
        listener.onDigitalStateChange(null);
        
        // Wait for timeout to occur
        try {
            Thread.sleep(100); // Wait longer than the timeout
        } catch (InterruptedException e) {
            logger.error("Sleep interrupted", e);
        }
        
        // Check that the sensor reports an error value
        assertEquals(-1.0, sensor.getDistance(), "Should return error value after timeout");
    }
}