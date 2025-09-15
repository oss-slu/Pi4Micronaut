package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import java.lang.reflect.Method;
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
    }

    @Test
    void testInitialization() {
        verify(mockTriggerPin).low();
        assertEquals(0.0, sensorHelper.getDistanceInCentimeter());
    }

    @Test
    void testStartMeasuring() throws InterruptedException {
        sensorHelper.stopMeasuring();
        sensorHelper.startMeasuring();

        assertDoesNotThrow(sensorHelper::startMeasuring);
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

        long durationInNano = TimeUnit.MICROSECONDS.toNanos(580);
        method.invoke(sensorHelper, durationInNano);

        assertEquals(10.0, sensorHelper.getDistanceInCentimeter(), 0.5);
    }


    @Test
    void testgetDistanceInCentemeter() throws Exception {
        Method method = UltraSonicSensorHelper.class.getDeclaredMethod("calculateDistance", long.class);
        method.setAccessible(true);

        long durationInNano = TimeUnit.MICROSECONDS.toNanos(290);
        method.invoke(sensorHelper, durationInNano);

        assertEquals(5.0, sensorHelper.getDistanceInCentimeter(), 0.5);
    }

    @Test
    void testgetDistanceInMeters() throws Exception {
        Method method = UltraSonicSensorHelper.class.getDeclaredMethod("calculateDistance", long.class);
        method.setAccessible(true);

        long durationInNano = TimeUnit.MICROSECONDS.toNanos(580);
        method.invoke(sensorHelper, durationInNano);

        assertEquals(0.10, sensorHelper.getDistanceInMeters(), 0.5);
    }

    @Test
    void stopMeasuring() {
        assertDoesNotThrow(sensorHelper::stopMeasuring);
        assertDoesNotThrow(sensorHelper::stopMeasuring);
    }
}
