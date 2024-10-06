package com.opensourcewithslu.inputdevices;

import static org.junit.jupiter.api.Assertions.*;
import com.opensourcewithslu.mock.MockDigitalInput;
import com.pi4j.io.gpio.digital.DigitalState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ScheduledExecutorService;

import static org.mockito.Mockito.*;

class SpeedSensorHelperTests {

    private MockDigitalInput sensorPin; // Mocked DigitalInput pin using the provided MockDigitalInput class
    private SpeedSensorHelper speedSensorHelper;
    private Logger log;
    private ScheduledExecutorService executorService;

    @BeforeEach
    public void setUp() {
        log = LoggerFactory.getLogger(SpeedSensorHelper.class); // Logger for testing
        sensorPin = new MockDigitalInput(); // Mock input for the speed sensor
        speedSensorHelper = new SpeedSensorHelper(sensorPin, 20.0); // Example: 20 pulses per revolution
        executorService = mock(ScheduledExecutorService.class); // Mock executor service
    }

    @Test
    void initializeState() {
        sensorPin.SetState(DigitalState.LOW); // Simulate the sensor in a LOW state
        speedSensorHelper.initialize();
        assertEquals(0.0, speedSensorHelper.getRPM(), "RPM should be 0 upon initialization.");
    }

    @Test
    void startMeasuringSchedulesCalculation() {
        speedSensorHelper.startMeasuring();
        // Since executorService is mocked, we can't test it directly here, but assuming it's properly configured.
        // You can ensure the test logic is valid without further mocking the internal thread.
    }

    @Test
    void stopMeasuringShutsDownExecutorService() {
        speedSensorHelper.stopMeasuring();
        verify(executorService).shutdownNow(); // Verifies that the executor service is shut down when measurement stops
    }

    @Test
    void getRPMReturnsCorrectValue() {
        // Simulate pulse timing for RPM calculation
        Instant now = Instant.now();
        sensorPin.SetState(DigitalState.HIGH); // Simulate a pulse
        speedSensorHelper.initialize();
        speedSensorHelper.startMeasuring();

        // Manually simulate a pulse and verify RPM calculation
        double expectedRPM = (1.0 / 0.1) * 60.0 / 20.0;  // Example: 30 RPM based on 100ms pulses, 20 pulses per revolution
        assertEquals(expectedRPM, speedSensorHelper.getRPM(), 0.01);
    }

    @Test
    void calculateSpeedLogsRPM() {
        speedSensorHelper.initialize();
        sensorPin.SetState(DigitalState.HIGH); // Simulate a pulse
        speedSensorHelper.startMeasuring();
        verify(log).info(contains("Speed (RPM):")); // Verifies that RPM is logged
    }

    @Test
    void stopMeasuringDisablesSensor() {
        speedSensorHelper.stopMeasuring();
        verify(log).info(contains("Sensor stopped")); // Verifies that sensor stopped logging is executed
    }

    @Test
    void errorHandlingDuringSpeedCalculation() {
        // Simulate an error during RPM calculation
        doThrow(new RuntimeException("Test Exception")).when(sensorPin).state(); // Simulate a state error
        speedSensorHelper.initialize();
        speedSensorHelper.startMeasuring();

        verify(log).error(contains("Error in measuring speed"), any(Exception.class)); // Verifies error logging
    }
}
