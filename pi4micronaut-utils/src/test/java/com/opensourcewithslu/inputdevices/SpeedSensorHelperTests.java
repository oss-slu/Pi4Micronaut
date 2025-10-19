package com.opensourcewithslu.inputdevices;

import com.opensourcewithslu.mock.MockDigitalInput;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpeedSensorHelperTests {

    private SpeedSensorHelper speedSensorHelper;
    private MockDigitalInput mockDigitalInput;

    @BeforeEach
    public void setUp() {
        mockDigitalInput = new MockDigitalInput();
        speedSensorHelper = new SpeedSensorHelper(mockDigitalInput, 20.0);
    }

    @Test
    public void testInitialization() {
        speedSensorHelper.initialize();
        // Verify that sensor is initialized correctly
        assertEquals(0.0, speedSensorHelper.getRPM());
    }
    /*
    @Test
    public void testRPMCalculation() throws InterruptedException {
        // Simulate pulses by updating the time
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            mockDigitalInput.SetState(DigitalState.HIGH);
            mockDigitalInput.SetState(DigitalState.LOW);
        }, 0, 100, TimeUnit.MILLISECONDS);

        speedSensorHelper.startMeasuring();

        // Increase sleep duration to allow the RPM to stabilize
        Thread.sleep(1500);  // Increase from 500ms to 1500ms

        // RPM should now be calculated based on simulated pulses
        double rpm = speedSensorHelper.getRPM();
        System.out.println("Calculated RPM: " + rpm);

        // The expected RPM should be approximately 30
        //assertEquals(30.0, rpm, 5.0);  // Keep a small margin for error

        speedSensorHelper.stopMeasuring();
        executorService.shutdown();
    }
    */
    /*
    @Test
    public void testStopMeasuring() {
        speedSensorHelper.startMeasuring();
        speedSensorHelper.stopMeasuring();
        // Verify that RPM stops being updated after stop
        double rpm = speedSensorHelper.getRPM();
        assertEquals(0.0, rpm);
    }
     */
}