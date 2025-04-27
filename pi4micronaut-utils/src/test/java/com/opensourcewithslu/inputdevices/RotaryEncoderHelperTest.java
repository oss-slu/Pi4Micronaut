import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.opensourcewithslu.utilities.MultiPinConfiguration;
import com.opensourcewithslu.inputdevices.RotaryEncoderHelper;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.DigitalStateChangeEvent;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;

public class RotaryEncoderHelperTest {
    private DigitalInput swPin;
    private DigitalInput clkPin;
    private DigitalInput dtPin;
    private RotaryEncoderHelper rotaryEncoderHelper;
    private MultiPinConfiguration multiPinConfig;

    @BeforeEach
    void setUp() {
        // Initialize mock pins
        swPin = mock(DigitalInput.class);
        clkPin = mock(DigitalInput.class);
        dtPin = mock(DigitalInput.class);

        // Mock state behavior
        when(swPin.state()).thenReturn(DigitalState.LOW);
        when(clkPin.state()).thenReturn(DigitalState.LOW);
        when(dtPin.state()).thenReturn(DigitalState.LOW);

        // Create MultiPinConfiguration with mock pins
        DigitalInput[] pins = {swPin, clkPin, dtPin};
        multiPinConfig = new MultiPinConfiguration("testEncoderHelper", pins);

        // Initialize RotaryEncoderHelper
        rotaryEncoderHelper = new RotaryEncoderHelper(multiPinConfig);
    }

    @Test
    void testCounterIncrement() {
        // Set states for both pins to HIGH to match the condition for increment
        when(clkPin.state()).thenReturn(DigitalState.HIGH);
        when(dtPin.state()).thenReturn(DigitalState.HIGH);
        when(clkPin.equals(DigitalState.HIGH)).thenReturn(true);

        // Capture and trigger clk listener
        ArgumentCaptor<DigitalStateChangeListener> listenerCaptor = ArgumentCaptor.forClass(DigitalStateChangeListener.class);
        verify(clkPin).addListener(listenerCaptor.capture());
        listenerCaptor.getValue().onDigitalStateChange(new DigitalStateChangeEvent<DigitalInput>(clkPin, DigitalState.HIGH));

        assertEquals(1, rotaryEncoderHelper.getEncoderValue(), "Counter should increment when clk and dt are both HIGH");
    }

    @Test
    void testCounterDecrement() {
        // Set states for pins to different values to match the condition for decrement
        when(clkPin.state()).thenReturn(DigitalState.HIGH);
        when(dtPin.state()).thenReturn(DigitalState.LOW);
        when(clkPin.equals(DigitalState.LOW)).thenReturn(false);

        // Capture and trigger clk listener
        ArgumentCaptor<DigitalStateChangeListener> listenerCaptor = ArgumentCaptor.forClass(DigitalStateChangeListener.class);
        verify(clkPin).addListener(listenerCaptor.capture());
        listenerCaptor.getValue().onDigitalStateChange(new DigitalStateChangeEvent<DigitalInput>(clkPin, DigitalState.HIGH));

        assertEquals(-1, rotaryEncoderHelper.getEncoderValue(), "Counter should decrement when clk and dt states are different");
    }

    // Add other tests similarly
}