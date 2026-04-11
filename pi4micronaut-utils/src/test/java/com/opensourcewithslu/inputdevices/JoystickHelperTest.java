package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JoystickHelperTest {

    private ADC0834ConverterHelper mockAdc;
    private DigitalInput mockSwInput;
    private JoystickHelper joystickHelper;

    @BeforeEach
    void setUp() {
        mockAdc = mock(ADC0834ConverterHelper.class);
        mockSwInput = mock(DigitalInput.class);
        when(mockSwInput.isLow()).thenReturn(false); // button not pressed by default
        joystickHelper = new JoystickHelper(mockAdc, mockSwInput);
    }

    // -------------------------------------------------------------------------
    // Constructor / initialize()
    // -------------------------------------------------------------------------

    @Test
    void constructorSetsisPressedFromDigitalInputState() {
        when(mockSwInput.isLow()).thenReturn(true);
        JoystickHelper helper = new JoystickHelper(mockAdc, mockSwInput);
        assertTrue(helper.isPressed, "isPressed should be true when SW input is LOW");
    }

    @Test
    void constructorSetsisPressedFalseWhenInputIsHigh() {
        when(mockSwInput.isLow()).thenReturn(false);
        JoystickHelper helper = new JoystickHelper(mockAdc, mockSwInput);
        assertFalse(helper.isPressed, "isPressed should be false when SW input is HIGH");
    }

    @Test
    void initializeAddsListenerToSwInput() {
        verify(mockSwInput, atLeastOnce()).addListener(any(DigitalStateChangeListener.class));
    }

    // -------------------------------------------------------------------------
    // getXValue()
    // -------------------------------------------------------------------------

    @Test
    void getXValueReadsFromChannel0() {
        when(mockAdc.readValue(JoystickHelper.X_AXIS_CHANNEL)).thenReturn(100);
        int x = joystickHelper.getXValue();
        assertEquals(100, x);
        verify(mockAdc).readValue(0);
    }

    @Test
    void getXValueReturnsMinValue() {
        when(mockAdc.readValue(0)).thenReturn(0);
        assertEquals(0, joystickHelper.getXValue());
    }

    @Test
    void getXValueReturnsMaxValue() {
        when(mockAdc.readValue(0)).thenReturn(255);
        assertEquals(255, joystickHelper.getXValue());
    }

    @Test
    void getXValueReturnsCenterValue() {
        when(mockAdc.readValue(0)).thenReturn(128);
        assertEquals(128, joystickHelper.getXValue());
    }

    // -------------------------------------------------------------------------
    // getYValue()
    // -------------------------------------------------------------------------

    @Test
    void getYValueReadsFromChannel1() {
        when(mockAdc.readValue(JoystickHelper.Y_AXIS_CHANNEL)).thenReturn(200);
        int y = joystickHelper.getYValue();
        assertEquals(200, y);
        verify(mockAdc).readValue(1);
    }

    @Test
    void getYValueReturnsMinValue() {
        when(mockAdc.readValue(1)).thenReturn(0);
        assertEquals(0, joystickHelper.getYValue());
    }

    @Test
    void getYValueReturnsMaxValue() {
        when(mockAdc.readValue(1)).thenReturn(255);
        assertEquals(255, joystickHelper.getYValue());
    }

    // -------------------------------------------------------------------------
    // getDirection()
    // -------------------------------------------------------------------------

    @Test
    void getDirectionReturnsCenter() {
        when(mockAdc.readValue(0)).thenReturn(128);
        when(mockAdc.readValue(1)).thenReturn(128);
        assertEquals("CENTER", joystickHelper.getDirection());
    }

    @Test
    void getDirectionReturnsUp() {
        when(mockAdc.readValue(0)).thenReturn(128);
        when(mockAdc.readValue(1)).thenReturn(0); // y < 78
        assertEquals("UP", joystickHelper.getDirection());
    }

    @Test
    void getDirectionReturnsDown() {
        when(mockAdc.readValue(0)).thenReturn(128);
        when(mockAdc.readValue(1)).thenReturn(255); // y > 178
        assertEquals("DOWN", joystickHelper.getDirection());
    }

    @Test
    void getDirectionReturnsLeft() {
        when(mockAdc.readValue(0)).thenReturn(0); // x < 78
        when(mockAdc.readValue(1)).thenReturn(128);
        assertEquals("LEFT", joystickHelper.getDirection());
    }

    @Test
    void getDirectionReturnsRight() {
        when(mockAdc.readValue(0)).thenReturn(255); // x > 178
        when(mockAdc.readValue(1)).thenReturn(128);
        assertEquals("RIGHT", joystickHelper.getDirection());
    }

    @Test
    void getDirectionReturnsUpLeft() {
        when(mockAdc.readValue(0)).thenReturn(0);
        when(mockAdc.readValue(1)).thenReturn(0);
        assertEquals("UP-LEFT", joystickHelper.getDirection());
    }

    @Test
    void getDirectionReturnsUpRight() {
        when(mockAdc.readValue(0)).thenReturn(255);
        when(mockAdc.readValue(1)).thenReturn(0);
        assertEquals("UP-RIGHT", joystickHelper.getDirection());
    }

    @Test
    void getDirectionReturnsDownLeft() {
        when(mockAdc.readValue(0)).thenReturn(0);
        when(mockAdc.readValue(1)).thenReturn(255);
        assertEquals("DOWN-LEFT", joystickHelper.getDirection());
    }

    @Test
    void getDirectionReturnsDownRight() {
        when(mockAdc.readValue(0)).thenReturn(255);
        when(mockAdc.readValue(1)).thenReturn(255);
        assertEquals("DOWN-RIGHT", joystickHelper.getDirection());
    }

    @Test
    void getDirectionReturnsCenterWithinDeadzone() {
        // Values within ±50 of center (128) should be CENTER
        when(mockAdc.readValue(0)).thenReturn(150);
        when(mockAdc.readValue(1)).thenReturn(110);
        assertEquals("CENTER", joystickHelper.getDirection());
    }

    // -------------------------------------------------------------------------
    // isButtonPressed()
    // -------------------------------------------------------------------------

    @Test
    void isButtonPressedReturnsTrueWhenInputIsLow() {
        when(mockSwInput.isLow()).thenReturn(true);
        assertTrue(joystickHelper.isButtonPressed());
    }

    @Test
    void isButtonPressedReturnsFalseWhenInputIsHigh() {
        when(mockSwInput.isLow()).thenReturn(false);
        assertFalse(joystickHelper.isButtonPressed());
    }

    @Test
    void isButtonPressedUpdatesisPressedField() {
        when(mockSwInput.isLow()).thenReturn(true);
        joystickHelper.isButtonPressed();
        assertTrue(joystickHelper.isPressed);
    }

    // -------------------------------------------------------------------------
    // addEventListener() / removeEventListener()
    // -------------------------------------------------------------------------

    @Test
    void addEventListenerAttachesListenerToSwInput() {
        DigitalStateChangeListener listener = mock(DigitalStateChangeListener.class);
        joystickHelper.addEventListener(listener);
        verify(mockSwInput).addListener(listener);
    }

    @Test
    void removeEventListenerDetachesInternalListener() {
        ArgumentCaptor<DigitalStateChangeListener> captor = ArgumentCaptor.forClass(DigitalStateChangeListener.class);
        verify(mockSwInput, atLeastOnce()).addListener(captor.capture());

        joystickHelper.removeEventListener();
        verify(mockSwInput).removeListener(captor.getValue());
    }

    @Test
    void removeEventListenerIsNoOpWhenCalledTwice() {
        joystickHelper.removeEventListener();
        joystickHelper.removeEventListener(); // second call should not throw
        verify(mockSwInput, times(1)).removeListener(any());
    }

    @Test
    void internalListenerUpdatesisPressedOnStateChange() {
        ArgumentCaptor<DigitalStateChangeListener> captor = ArgumentCaptor.forClass(DigitalStateChangeListener.class);
        verify(mockSwInput, atLeastOnce()).addListener(captor.capture());
        DigitalStateChangeListener internalListener = captor.getValue();

        when(mockSwInput.isLow()).thenReturn(true);
        internalListener.onDigitalStateChange(null);
        assertTrue(joystickHelper.isPressed);

        when(mockSwInput.isLow()).thenReturn(false);
        internalListener.onDigitalStateChange(null);
        assertFalse(joystickHelper.isPressed);
    }

    // -------------------------------------------------------------------------
    // Constants
    // -------------------------------------------------------------------------

    @Test
    void xAxisChannelIsZero() {
        assertEquals(0, JoystickHelper.X_AXIS_CHANNEL);
    }

    @Test
    void yAxisChannelIsOne() {
        assertEquals(1, JoystickHelper.Y_AXIS_CHANNEL);
    }

    @Test
    void centerValueIs128() {
        assertEquals(128, JoystickHelper.CENTER_VALUE);
    }
}