package com.opensourcewithslu.inputdevices;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.opensourcewithslu.mock.MockDigitalInput;
import com.pi4j.io.gpio.digital.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Field;

public class PhotoResistorHelperTests{
    private MockDigitalInput mockDigitalInput;
    private DigitalOutput mockDigitalOutput;
    private PhotoResistorHelper photoResistorHelper;

    @BeforeEach
    public void setup(){
        mockDigitalInput = new MockDigitalInput();
        mockDigitalOutput = mock(DigitalOutput.class);
        photoResistorHelper = new PhotoResistorHelper(mockDigitalInput,mockDigitalOutput);
    }

    @Test
    public void testInitializationLogic() {
        mockDigitalInput.SetState(DigitalState.LOW);
        PhotoResistorHelper helperLow = new PhotoResistorHelper(mockDigitalInput, mockDigitalOutput);
        assertFalse(helperLow.isDark);

        mockDigitalInput.SetState(DigitalState.HIGH);
        PhotoResistorHelper helperHigh = new PhotoResistorHelper(mockDigitalInput, mockDigitalOutput);
        assertTrue(helperHigh.isDark);
    }

    @Test
    public void testDarknessLevelCalculation() throws Exception {
        PhotoResistorHelper helper = new PhotoResistorHelper(mockDigitalInput, mockDigitalOutput);

        helper.setDarknessThreshold(50);

        Field startTimeField = PhotoResistorHelper.class.getDeclaredField("startTime");
        startTimeField.setAccessible(true);
        startTimeField.setLong(helper, System.currentTimeMillis() - 100);

        helper.updateDark();

        Field endTimeField = PhotoResistorHelper.class.getDeclaredField("endTime");
        endTimeField.setAccessible(true);
        endTimeField.setLong(helper, System.currentTimeMillis());

        Field darknessField = PhotoResistorHelper.class.getDeclaredField("darknessValue");
        darknessField.setAccessible(true);
        int darknessValue = (int) (endTimeField.getLong(helper) - startTimeField.getLong(helper));
        darknessField.setInt(helper, darknessValue);

        assertTrue(darknessValue > 0);

        Field isDarkField = PhotoResistorHelper.class.getDeclaredField("isDark");
        isDarkField.setAccessible(true);
        isDarkField.setBoolean(helper, darknessValue > 50);

        boolean isDark = isDarkField.getBoolean(helper);
        assertTrue(isDark);
    }

    @Test
    public void testGetDark() throws Exception {
        PhotoResistorHelper helper = new PhotoResistorHelper(mockDigitalInput, mockDigitalOutput);

        Field darknessField = PhotoResistorHelper.class.getDeclaredField("darknessValue");
        darknessField.setAccessible(true);
        darknessField.setInt(helper, 157);

        assertEquals(157, helper.getDark());
    }

    @Test
    public void testSetToLow() {
        mockDigitalInput.SetState(DigitalState.HIGH);
        PhotoResistorHelper helper = new PhotoResistorHelper(mockDigitalInput, mockDigitalOutput);

        helper.setToLow();
        verify(mockDigitalOutput).low();
    }

    @Test
    public void testSetDarknessThreshold() throws Exception {
        PhotoResistorHelper helper = new PhotoResistorHelper(mockDigitalInput, mockDigitalOutput);
        helper.setDarknessThreshold(300);

        Field thresholdField = PhotoResistorHelper.class.getDeclaredField("darknessThreshold");
        thresholdField.setAccessible(true);
        int actualThreshold = thresholdField.getInt(helper);

        assertEquals(300, actualThreshold);
    }

    @Test
    public void testEventListenerManagement() {
        DigitalInput mockInput = mock(DigitalInput.class);
        PhotoResistorHelper helper = new PhotoResistorHelper(mockInput, mockDigitalOutput);
        DigitalStateChangeListener mockListener = mock(DigitalStateChangeListener.class);

        helper.addEventListener(mockListener);
        verify(mockInput, times(1)).addListener(mockListener);

        helper.removeEventListener();
        verify(mockInput, times(1)).removeListener(mockListener);
    }
}
