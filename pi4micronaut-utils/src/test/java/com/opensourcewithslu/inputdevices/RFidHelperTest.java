package com.opensourcewithslu.inputdevices;

import com.pi4j.context.Context;
import com.pi4j.crowpi.components.RfidComponent;
import com.pi4j.io.spi.SpiConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RFidHelperTest {

    private static final int RESET_PIN = 25;

    private Context mockContext;
    private SpiConfig mockSpiConfig;

    @BeforeEach
    void setUp() {
        mockContext = Mockito.mock(Context.class);
        mockSpiConfig = Mockito.mock(SpiConfig.class);
        when(mockSpiConfig.getAddress()).thenReturn(0);
        when(mockSpiConfig.getBaud()).thenReturn(1_000_000);
    }

    // Constructor creates RfidComponent when reset pin is provided
    @Test
    void constructor_WithResetPin_CreatesComponent() {
        try (MockedConstruction<RfidComponent> mocked = mockConstruction(RfidComponent.class)) {
            new RFidHelper(mockSpiConfig, RESET_PIN, mockContext);
            assertEquals(1, mocked.constructed().size());
        }
    }

    // Constructor creates RfidComponent without reset pin
    @Test
    void constructor_WithoutResetPin_CreatesComponent() {
        try (MockedConstruction<RfidComponent> mocked = mockConstruction(RfidComponent.class)) {
            new RFidHelper(mockSpiConfig, mockContext);
            assertEquals(1, mocked.constructed().size());
        }
    }

    // Constructor throws NPE if context is null
    @Test
    void constructor_NullContext_ThrowsNPE() {
        assertThrows(NullPointerException.class,
                () -> new RFidHelper(mockSpiConfig, RESET_PIN, null));
    }

    // Constructor throws NPE if SpiConfig is null
    @Test
    void constructor_NullSpiConfig_ThrowsNPE() {
        assertThrows(NullPointerException.class,
                () -> new RFidHelper(null, RESET_PIN, mockContext));
    }

    // writeToCard calls waitForAnyCard on the component
    @Test
    void writeToCard_CallsWaitForAnyCard() {
        try (MockedConstruction<RfidComponent> mocked = mockConstruction(RfidComponent.class,
                (mock, ctx) -> doNothing().when(mock).waitForAnyCard(any()))) {

            RFidHelper helper = new RFidHelper(mockSpiConfig, RESET_PIN, mockContext);
            helper.writeToCard("test");

            RfidComponent component = mocked.constructed().get(0);
            verify(component).waitForAnyCard(any());
        }
    }

    // readFromCard calls waitForAnyCard on the component
    @Test
    void readFromCard_CallsWaitForAnyCard() {
        try (MockedConstruction<RfidComponent> mocked = mockConstruction(RfidComponent.class,
                (mock, ctx) -> doNothing().when(mock).waitForAnyCard(any()))) {

            RFidHelper helper = new RFidHelper(mockSpiConfig, RESET_PIN, mockContext);
            helper.readFromCard();

            RfidComponent component = mocked.constructed().get(0);
            verify(component).waitForAnyCard(any());
        }
    }

    // resetScanner calls reset() on the component
    @Test
    void resetScanner_CallsReset() {
        try (MockedConstruction<RfidComponent> mocked = mockConstruction(RfidComponent.class)) {
            RFidHelper helper = new RFidHelper(mockSpiConfig, RESET_PIN, mockContext);
            helper.resetScanner();
            verify(mocked.constructed().get(0)).reset();
        }
    }
}