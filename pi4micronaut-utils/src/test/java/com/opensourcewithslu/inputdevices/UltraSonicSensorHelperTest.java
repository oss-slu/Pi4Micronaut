package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class UltraSonicSensorHelperTest {

    private DigitalOutput mockTriggerPin;
    private DigitalInput mockEchoPin;
    private UltraSonicSensorHelper sensorHelper;

    Logger log = mock(Logger.class);

    @BeforeEach
    void setUp() {
        mockTriggerPin = Mockito.mock(DigitalOutput.class);
        mockEchoPin = Mockito.mock(DigitalInput.class);

        sensorHelper = new UltraSonicSensorHelper(mockTriggerPin, mockEchoPin);

        sensorHelper.setLog(log);
    }

    @AfterEach
    void tearDown() {
        sensorHelper.stopMeasuring();
    }

    @Test
    void testInitialization() {
        verify(mockTriggerPin).low();

        assertEquals(0.0, sensorHelper.getDistanceInCentimeter());
    }

    @Test
    void testStartMeasuringWhenSensorInactiveDoesNothing() throws Exception {
        Field sensorActiveField = UltraSonicSensorHelper.class.getDeclaredField("sensorActive");
        sensorActiveField.setAccessible(true);

        sensorActiveField.set(sensorHelper, false);
        Mockito.clearInvocations(mockTriggerPin, mockEchoPin);
        sensorHelper.startMeasuring();Thread.sleep(150);
        
        assertEquals(0.0, sensorHelper.getDistanceInCentimeter(), 0.01);

        verifyNoMoreInteractions(mockTriggerPin, mockEchoPin);

        assertFalse((boolean) sensorActiveField.get(sensorHelper));
    }

    @Test
    void testStartMeasuringWhenSensorActiveStartsMeasurements() throws Exception {
        Field sensorActiveField = UltraSonicSensorHelper.class.getDeclaredField("sensorActive");
        sensorActiveField.setAccessible(true);
        sensorActiveField.set(sensorHelper, true);

        Mockito.clearInvocations(mockTriggerPin, mockEchoPin);

        when(mockEchoPin.isHigh()).thenReturn(true, false);
        sensorHelper.startMeasuring();
        Thread.sleep(150);

        assertTrue(sensorHelper.getDistanceInCentimeter() >= 0);

        verify(log).info("Ultrasonic Sensor Measurement Started");
    }

    @Test
    void testStartMeasuringWhenInactive() {
        sensorHelper.stopMeasuring();
        Mockito.clearInvocations(mockTriggerPin, mockEchoPin);
        sensorHelper.startMeasuring();

        verifyNoMoreInteractions(mockTriggerPin, mockEchoPin);
    }

    @Test
    void testTriggerAndMeasureDistanceTimeout() throws Exception {
        Method method = UltraSonicSensorHelper.class.getDeclaredMethod("triggerAndMeasureDistance");
        method.setAccessible(true);
        when(mockEchoPin.isHigh()).thenReturn(true);

        assertDoesNotThrow(() -> method.invoke(sensorHelper));
    }

    @Test
    void testCalculateDistanceWithNegativeDuration() throws Exception {
        Method method = UltraSonicSensorHelper.class.getDeclaredMethod("calculateDistance", long.class);
        method.setAccessible(true);
        method.invoke(sensorHelper, -100L);

        assertEquals(0.0, sensorHelper.getDistanceInCentimeter(), 0.01);
    }

    @Test
    void testStopMeasuringDeactivatesSensor() {
        sensorHelper.startMeasuring();
        sensorHelper.stopMeasuring();
        Mockito.clearInvocations(mockTriggerPin, mockEchoPin);
        sensorHelper.startMeasuring();

        verifyNoMoreInteractions(mockTriggerPin, mockEchoPin);
    }   

    @Test
    void testTriggerAndMeasureDistance() throws Exception {
        Method method = UltraSonicSensorHelper.class.getDeclaredMethod("triggerAndMeasureDistance");
        method.setAccessible(true);
        when(mockTriggerPin.isHigh()).thenReturn(true);
        when(mockEchoPin.isHigh()).thenReturn(true).thenReturn(false);
        method.invoke(sensorHelper);

        assertTrue(sensorHelper.getDistanceInCentimeter() >= 0);
    }

    @Test
    void testCalculateDistance() throws Exception {
        Method method = UltraSonicSensorHelper.class.getDeclaredMethod("calculateDistance", long.class);
        method.setAccessible(true);

        long durationInNano = TimeUnit.MICROSECONDS.toNanos(580); //time for sound to travel to an object 10 cm away and back
        method.invoke(sensorHelper, durationInNano);

        assertEquals(10.0, sensorHelper.getDistanceInCentimeter(), 0.5);

        verify(log).info("Distance measured: {} cm", 10.0);
    }


    @Test
    void testgetDistanceInCentemeter() throws Exception {
        Method method = UltraSonicSensorHelper.class.getDeclaredMethod("calculateDistance", long.class);
        method.setAccessible(true);

        long durationInNano = TimeUnit.MICROSECONDS.toNanos(290); //time for sound to travel to an object 5 cm away and back
        method.invoke(sensorHelper, durationInNano);

        assertEquals(5.0, sensorHelper.getDistanceInCentimeter(), 0.5);
    }

    @Test
    void testgetDistanceInMeters() throws Exception {
        Method method = UltraSonicSensorHelper.class.getDeclaredMethod("calculateDistance", long.class);
        method.setAccessible(true);

        long durationInNano = TimeUnit.MICROSECONDS.toNanos(580); //time for sound to travel to an object 0.10 m away and back
        method.invoke(sensorHelper, durationInNano);

        assertEquals(0.10, sensorHelper.getDistanceInMeters(), 0.5);
    }

    @Test
    void stopMeasuring() throws Exception {
        when(mockEchoPin.isHigh()).thenReturn(true, false).thenReturn(true, false); 
        Thread.sleep(150);

        double distanceBefore = sensorHelper.getDistanceInCentimeter();

        sensorHelper.stopMeasuring();
        verify(log).info("Ultrasonic Sensor Measurement Stopped");

        
        when(mockEchoPin.isHigh()).thenReturn(true, false);
        Thread.sleep(150);

        double distanceAfter = sensorHelper.getDistanceInCentimeter();

        assertEquals(distanceBefore, distanceAfter, 0.01);
    }
}
