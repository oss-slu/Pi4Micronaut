package com.opensourcewithslu.outputdevices;

import com.opensourcewithslu.utilities.MultiPinConfiguration;
import com.pi4j.io.pwm.Pwm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

public class RGBLEDHelperTest {

    @Mock
    private MultiPinConfiguration pwmConfig;

    @Mock
    private Pwm redPwm;

    @Mock
    private Pwm greenPwm;

    @Mock
    private Pwm bluePwm;

    private RGBLEDHelper helper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        when(pwmConfig.getComponents()).thenReturn(new Pwm[]{ redPwm, greenPwm, bluePwm });
        helper = new RGBLEDHelper(pwmConfig);
    }

    @Test
    void testLedOffTurnsAllOff() {
        helper.ledOff();
        verify(redPwm).off();
        verify(greenPwm).off();
        verify(bluePwm).off();
        verifyNoMoreInteractions(redPwm, greenPwm, bluePwm);
    }

    @Test
    void testLedOnSetDefaults() {
        helper.ledOn();
        verify(redPwm).on(100, 200);
        verify(greenPwm).on(100,200);
        verify(bluePwm).on(100, 200);
    }

    @Test
    void testSetColorDefaultFrequency() {
        int[] colors = {10, 20, 30};
        helper.setColor(colors);
        verify(redPwm).on(10, 200);
        verify(greenPwm).on(20, 200);
        verify(bluePwm).on(30, 200);
    }

    @Test
    void testSetColorWithFrequencies() {
        int[] colors = {5, 15, 25};
        int[] freqs = {100, 150, 175};
        helper.setColor(colors, freqs);
        verify(redPwm).on(5, 100);
        verify(greenPwm).on(15, 150);
        verify(bluePwm).on(25, 175);
    }

    @Test
    void testSetColorHexDefaultFrequency() {
        // hex 0x112233 -> R = 17, G = 34, B = 51
        helper.setColorHex("0x112233");
        verify(redPwm).on(17,200);
        verify(greenPwm).on(34, 200);
        verify(bluePwm).on(51, 200);
    }

    @Test
    void testSetRedDefaultFreq() {
        helper.setRed(123);
        verify(redPwm).on(123, 200);
        verifyNoInteractions(greenPwm, bluePwm);
    }

    @Test
    void testSetRedWithFreq() {
        helper.setRed(45, 300);
        verify(redPwm).on(45, 300);
    }

    @Test
    void testSetGreenDefaultFreq() {
        helper.setGreen(77);
        verify(greenPwm).on(77, 200);
        verifyNoInteractions(redPwm, bluePwm);
    }

    @Test
    void testSetGreenWithFreq() {
        helper.setGreen(88,400);
        verify(greenPwm).on(88, 400);
    }

    @Test
    void testSetBlueDefaultFreq() {
        helper.setBlue(9);
        verify(bluePwm).on(9,200);
        verifyNoInteractions(redPwm, greenPwm);
    }

    @Test
    void testSetBlueWithFreq() {
        helper.setBlue(66, 500);
        verify(bluePwm).on(66, 500);
    }
}
