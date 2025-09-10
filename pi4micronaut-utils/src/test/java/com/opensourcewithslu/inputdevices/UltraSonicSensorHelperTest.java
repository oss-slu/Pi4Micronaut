package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UltraSonicSensorHelperTest {

    private DigitalOutput mockTriggerPin;
    private DigitalInput mockEchoPin;
    private UltraSonicSensorHelper sensorHelper;

    @BeforeEach
    void setUp() {
        // Create "fake" pins using Mockito
        mockTriggerPin = Mockito.mock(DigitalOutput.class);
        mockEchoPin = Mockito.mock(DigitalInput.class);

        // Pass them into the helper
        sensorHelper = new UltraSonicSensorHelper(mockTriggerPin, mockEchoPin);
    }

    @Test
}
