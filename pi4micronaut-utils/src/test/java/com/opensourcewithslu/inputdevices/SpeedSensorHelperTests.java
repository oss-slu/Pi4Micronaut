package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ScheduledExecutorService;

import static org.mockito.Mockito.*;

class SpeedSensorHelperTest {
    DigitalInput sensorPin = mock(DigitalInput.class); // Mocked DigitalInput pin
    SpeedSensorHelper speedSensorHelper = new SpeedSensorHelper(sensorPin, 20.0); // Example: 20 pulses per revolution
    Logger log = mock(Logger.class); // Mocked logger
    ScheduledExecutorService executorService = mock(ScheduledExecutorService.class);

    @BeforeEach
    public void setUp() {
        speedSensorHelper.setLog(log); // Set mock logger if there's a setLog method
    }

    @Test
    void startMeasuringSchedulesCalculation() {
        speedSensorHelper.startMeasuring();
        verify(executorService).scheduleAtFixedRate(any(), eq(0L), eq(100L), eq(java.util.concurrent.TimeUnit.MILLISECONDS));
    }

    @Test
    void stopMeasuringShutsDownExecutorService() {
        speedSensorHelper.stopMeasuring();
        verify(executorService).shutdownNow();
    }

    @Test
    void getRPMReturnsCorrectValue() {
        // Simulate lastPulseTime and currentTime for RPM calculation
        Instant now = Instant.now();
        Duration timeBetweenPulses = Duration.ofMillis(100); // 100 ms between pulses
        speedSensorHelper.initialize();
        speedSensorHelper.startMeasuring();

        // This assumes some way to manipulate time and calculate RPM, as in previous examples
        double expectedRPM = (1.0 / 0.1) * 60.0 / 20.0;  // 30 RPM based on the calculation
        assertEquals(expectedRPM, speedSensorHelper.getRPM(), 0.01);
    }

    @Test
    void calculateSpeedLogsRPM() {
        // Simulate a speed calculation
        speedSensorHelper.initialize();
        speedSensorHelper.startMeasuring();

        // Assuming calculateSpeed is exposed or can be tested indirectly
        speedSensorHelper.calculateSpeed();

        verify(log).info(contains("Speed (RPM):"));
    }

    @Test
    void stopMeasuringDisablesSensor() {
        speedSensorHelper.stopMeasuring();
        verify(log).info("Sensor stopped");
    }

    @Test
    void errorHandlingDuringSpeedCalculation() {
        // Simulate an error during speed calculation
        doThrow(new RuntimeException("Test Exception")).when(sensorPin).isLow();
        speedSensorHelper.initialize();
        speedSensorHelper.startMeasuring();

        verify(log).error("Error in measuring speed", any(Exception.class));
    }
}
