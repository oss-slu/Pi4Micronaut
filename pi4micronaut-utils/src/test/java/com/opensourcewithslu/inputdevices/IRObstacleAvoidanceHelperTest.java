package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class IRObstacleAvoidanceHelperTest {

    private DigitalInput mockDigitalInput;
    private IRObstacleAvoidanceHelper irObstacleAvoidanceHelper;

    @BeforeEach
    void setUp() {
        mockDigitalInput = mock(DigitalInput.class);
        irObstacleAvoidanceHelper = new IRObstacleAvoidanceHelper(mockDigitalInput);
    }

    @Test
    void testConstructor_withNullInput_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new IRObstacleAvoidanceHelper(null)
        );
        assertEquals("DigitalInput cannot be null for IR obstacle avoidance module", exception.getMessage());
    }

    @Test
    void testIsObstacleDetected_obstaclePresent() {
        when(mockDigitalInput.state()).thenReturn(DigitalState.LOW);
        
        boolean result = irObstacleAvoidanceHelper.isObstacleDetected();
        
        assertTrue(result);
    }

    @Test
    void testIsObstacleDetected_noObstacle() {
        when(mockDigitalInput.state()).thenReturn(DigitalState.HIGH);
        
        boolean result = irObstacleAvoidanceHelper.isObstacleDetected();
        
        assertFalse(result);
    }

    @Test
    void testGetState_returnsState() {
        when(mockDigitalInput.state()).thenReturn(DigitalState.LOW);
        
        DigitalState state = irObstacleAvoidanceHelper.getState();
        
        assertEquals(DigitalState.LOW, state);
    }

    @Test
    void testAddEventListener_withNullListener_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> irObstacleAvoidanceHelper.addEventListener(null)
        );
        assertEquals("Event listener cannot be null", exception.getMessage());
    }

    @Test
    void testAddEventListener_withValidListener() {
        IRObstacleAvoidanceHelper.ObstacleEventListener mockListener = mock(
                IRObstacleAvoidanceHelper.ObstacleEventListener.class
        );
        
        irObstacleAvoidanceHelper.addEventListener(mockListener);
        verify(mockDigitalInput).addListener(any());
    }

    @Test
    void testRemoveEventListener_successful() {
        IRObstacleAvoidanceHelper.ObstacleEventListener mockListener = mock(
                IRObstacleAvoidanceHelper.ObstacleEventListener.class
        );
        irObstacleAvoidanceHelper.addEventListener(mockListener);
        
        irObstacleAvoidanceHelper.removeEventListener();
        
        verify(mockDigitalInput).removeListener(any());
    }

    @Test
    void testWaitForObstacle_detectedWithinTimeout() throws InterruptedException {
        when(mockDigitalInput.state())
                .thenReturn(DigitalState.HIGH)
                .thenReturn(DigitalState.LOW);
        
        boolean result = irObstacleAvoidanceHelper.waitForObstacle(1000);
        
        assertTrue(result);
    }

    @Test
    void testWaitForObstacle_timeoutExpired() throws InterruptedException {
        when(mockDigitalInput.state()).thenReturn(DigitalState.HIGH);
        
        boolean result = irObstacleAvoidanceHelper.waitForObstacle(50);
        
        assertFalse(result);
    }

    @Test
    void testWaitForObstacle_negativeTimeout_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> irObstacleAvoidanceHelper.waitForObstacle(-1)
        );
        assertEquals("Timeout cannot be negative", exception.getMessage());
    }

    @Test
    void testGetStatusMessage_obstacleDetected() {
        when(mockDigitalInput.state()).thenReturn(DigitalState.LOW);
        irObstacleAvoidanceHelper.isObstacleDetected();
        
        String status = irObstacleAvoidanceHelper.getStatusMessage();
        
        assertEquals("Obstacle detected", status);
    }

    @Test
    void testGetStatusMessage_noObstacle() {
        when(mockDigitalInput.state()).thenReturn(DigitalState.HIGH);
        irObstacleAvoidanceHelper.isObstacleDetected();
        
        String status = irObstacleAvoidanceHelper.getStatusMessage();
        
        assertEquals("No obstacle", status);
    }

    @Test
    void testShutdown_successful() {
        assertDoesNotThrow(() -> irObstacleAvoidanceHelper.shutdown());
    }
}
