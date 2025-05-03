package com.opensourcewithslu.outputdevices;
import com.pi4j.io.pwm.Pwm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.concurrent.TimeUnit;
import static org.mockito.Mockito.*;

class ActiveBuzzerHelperTest {
    private Pwm mockPwm;
    private ActiveBuzzerHelper buzzerHelper;

    @BeforeEach
    void setUp() {
        mockPwm = Mockito.mock(Pwm.class);
        buzzerHelper = new ActiveBuzzerHelper(mockPwm);
    }

    @Test
    void testActiveBuzzerOn() {
        buzzerHelper.activeBuzzerOn();

        verify(mockPwm).on();
        verify(mockPwm).dutyCycle(50);
        verify(mockPwm).frequency(1000);
    }

    @Test
    void testActiveBuzzerOff() {
        buzzerHelper.activeBuzzerOff();

        verify(mockPwm).off();
    }

    @Test
    void testBeep() throws InterruptedException {
        buzzerHelper.beep();

        verify(mockPwm, atLeastOnce()).on();
        verify(mockPwm, atLeastOnce()).off();
    }

    @Test
    void testIntermittentTone() throws InterruptedException {
        buzzerHelper.intermittentTone();

        verify(mockPwm, atLeastOnce()).on();
        verify(mockPwm, atLeastOnce()).off();
    }

    @Test
    void testMorseCodeTone() throws InterruptedException {
        buzzerHelper.morseCodeTone();

        verify(mockPwm, atLeastOnce()).on();
        verify(mockPwm, atLeastOnce()).off();
    }
}
