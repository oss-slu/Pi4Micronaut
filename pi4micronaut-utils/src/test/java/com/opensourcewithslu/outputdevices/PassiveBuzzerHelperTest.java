package com.opensourcewithslu.outputdevices;

import com.pi4j.io.pwm.Pwm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PassiveBuzzerHelperTest {

    private Pwm mockPwm;
    private PassiveBuzzerHelper buzzer;

    @BeforeEach
    void setUp() {
        mockPwm = mock(Pwm.class);
        buzzer = new PassiveBuzzerHelper(mockPwm);
    }

    // -------------------------------------------------------------------------
    // Constructor / defaults
    // -------------------------------------------------------------------------

    @Test
    void defaultFrequencyIs440() {
        assertEquals(440, buzzer.passiveBuzzerFreq);
    }

    @Test
    void defaultDutyCycleIs50() {
        assertEquals(50, buzzer.passBuzzDC);
    }

    // -------------------------------------------------------------------------
    // passiveBuzzerOn()
    // -------------------------------------------------------------------------

    @Test
    void passiveBuzzerOnCallsPwmOnWithCorrectArguments() {
        buzzer.passiveBuzzerOn(50, 440);
        verify(mockPwm).on(50, 440);
    }

    @Test
    void passiveBuzzerOnWithCustomDutyCycleAndFrequency() {
        buzzer.passiveBuzzerOn(75, 880);
        verify(mockPwm).on(75, 880);
    }

    @Test
    void passiveBuzzerOnWithMinimumValues() {
        buzzer.passiveBuzzerOn(0, 20);
        verify(mockPwm).on(0, 20);
    }

    @Test
    void passiveBuzzerOnWithMaximumValues() {
        buzzer.passiveBuzzerOn(100, 20000);
        verify(mockPwm).on(100, 20000);
    }

    // -------------------------------------------------------------------------
    // passiveBuzzerOff()
    // -------------------------------------------------------------------------

    @Test
    void passiveBuzzerOffCallsPwmOff() {
        buzzer.passiveBuzzerOff();
        verify(mockPwm).off();
    }

    @Test
    void passiveBuzzerOffCanBeCalledMultipleTimes() {
        buzzer.passiveBuzzerOff();
        buzzer.passiveBuzzerOff();
        verify(mockPwm, times(2)).off();
    }

    // -------------------------------------------------------------------------
    // getFrequency()
    // -------------------------------------------------------------------------

    @Test
    void getFrequencyReturnsDefaultFrequency() {
        assertEquals(440, buzzer.passiveBuzzerFreq);
    }

    @Test
    void getFrequencyAfterManualChange() {
        buzzer.passiveBuzzerFreq = 880;
        assertEquals(880, buzzer.passiveBuzzerFreq);
    }

    // -------------------------------------------------------------------------
    // passiveBuzzTone()
    // -------------------------------------------------------------------------

    @Test
    void passiveBuzzToneCallsBuzzerOnThenOff() {
        buzzer.passiveBuzzTone();
        verify(mockPwm).on(buzzer.passBuzzDC, buzzer.passiveBuzzerFreq);
        verify(mockPwm).off();
    }

    @Test
    void passiveBuzzToneTurnsOnBeforeOff() {
        var order = inOrder(mockPwm);
        buzzer.passiveBuzzTone();
        order.verify(mockPwm).on(anyInt(), anyInt());
        order.verify(mockPwm).off();
    }

    @Test
    void passiveBuzzToneUsesDefaultFrequencyAndDutyCycle() {
        buzzer.passiveBuzzTone();
        verify(mockPwm).on(50, 440);
    }

    // -------------------------------------------------------------------------
    // toneIterator()
    // -------------------------------------------------------------------------

    @Test
    void toneIteratorCallsOnAndOffForEachFrequency() {
        buzzer.toneIterator();
        // 5 frequencies: 880, 1760, 3520, 9000, 15000
        verify(mockPwm, times(5)).on(anyInt(), anyInt());
        verify(mockPwm, times(5)).off();
    }

    @Test
    void toneIteratorPlaysEachFrequencyInOrder() {
        var order = inOrder(mockPwm);
        buzzer.toneIterator();
        order.verify(mockPwm).on(50, 880);
        order.verify(mockPwm).off();
        order.verify(mockPwm).on(50, 1760);
        order.verify(mockPwm).off();
        order.verify(mockPwm).on(50, 3520);
        order.verify(mockPwm).off();
        order.verify(mockPwm).on(50, 9000);
        order.verify(mockPwm).off();
        order.verify(mockPwm).on(50, 15000);
        order.verify(mockPwm).off();
    }

    @Test
    void toneIteratorTurnsOffAfterEachFrequency() {
        buzzer.toneIterator();
        // After each of the 5 frequencies, off() must be called
        verify(mockPwm, times(5)).off();
    }

    // -------------------------------------------------------------------------
    // piToneSequence()
    // -------------------------------------------------------------------------

    @Test
    void piToneSequenceCallsBuzzerOffAtEnd() {
        buzzer.piToneSequence();
        verify(mockPwm, atLeastOnce()).off();
    }

    @Test
    void piToneSequenceCallsOnForEachDigitOfPi() {
        buzzer.piToneSequence();
        // digits: {3,1,4,1,5,9,2,6,5} = 9 digits + 1 initial on = 10 total on calls
        verify(mockPwm, times(10)).on(anyInt(), anyInt());
    }

    @Test
    void piToneSequencePlaysCorrectFrequenciesForPiDigits() {
        // digitsOfPi = {3,1,4,1,5,9,2,6,5}
        // frequencies = {261,293,329,349,392,440,493,523,587,659}
        // digit 3 -> 349, digit 1 -> 293, digit 4 -> 392,
        // digit 1 -> 293, digit 5 -> 392 wait...
        // frequencies[3]=349, frequencies[1]=293, frequencies[4]=392,
        // frequencies[1]=293, frequencies[5]=440, frequencies[9]=659,
        // frequencies[2]=329, frequencies[6]=493, frequencies[5]=440
        var order = inOrder(mockPwm);
        buzzer.piToneSequence();
        order.verify(mockPwm).on(50, 440); // initial on with default freq
        order.verify(mockPwm).on(50, 349); // digit 3
        order.verify(mockPwm).on(50, 293); // digit 1
        order.verify(mockPwm).on(50, 392); // digit 4
        order.verify(mockPwm).on(50, 293); // digit 1
        order.verify(mockPwm).on(50, 440); // digit 5
        order.verify(mockPwm).on(50, 659); // digit 9
        order.verify(mockPwm).on(50, 329); // digit 2
        order.verify(mockPwm).on(50, 523); // digit 6
        order.verify(mockPwm).on(50, 440); // digit 5
        order.verify(mockPwm).off();
    }

    @Test
    void piToneSequenceEndsWithBuzzerOff() {
        var order = inOrder(mockPwm);
        buzzer.piToneSequence();
        // last interaction must be off()
        order.verify(mockPwm, atLeastOnce()).on(anyInt(), anyInt());
        order.verify(mockPwm).off();
    }

    // -------------------------------------------------------------------------
    // setFrequencies() — file-based
    // -------------------------------------------------------------------------

    @Test
    void setFrequenciesPlaysEachValidFrequency(@TempDir Path tempDir) throws IOException {
        File file = tempDir.resolve("freqs.txt").toFile();
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("440,880,1760");
        }
        buzzer.setFrequencies(file);
        verify(mockPwm, times(3)).on(anyInt(), anyInt());
        verify(mockPwm, times(3)).off();
    }

    @Test
    void setFrequenciesSkipsOutOfRangeFrequencies(@TempDir Path tempDir) throws IOException {
        File file = tempDir.resolve("freqs.txt").toFile();
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("10,440,25000"); // 10 and 25000 are out of range
        }
        buzzer.setFrequencies(file);
        // Only 440 is valid
        verify(mockPwm, times(1)).on(anyInt(), anyInt());
        verify(mockPwm, times(1)).off();
    }

    @Test
    void setFrequenciesHandlesFileNotFound() {
        File nonExistent = new File("/nonexistent/path/freqs.txt");
        // Should not throw — logs error instead
        assertDoesNotThrow(() -> buzzer.setFrequencies(nonExistent));
        verify(mockPwm, never()).on(anyInt(), anyInt());
    }

    @Test
    void setFrequenciesHandlesSingleFrequency(@TempDir Path tempDir) throws IOException {
        File file = tempDir.resolve("freqs.txt").toFile();
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("1000");
        }
        buzzer.setFrequencies(file);
        verify(mockPwm, times(1)).on(50, 1000);
        verify(mockPwm, times(1)).off();
    }

    @Test
    void setFrequenciesAcceptsBoundaryValues(@TempDir Path tempDir) throws IOException {
        File file = tempDir.resolve("freqs.txt").toFile();
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("20,20000"); // exact boundary values — both valid
        }
        buzzer.setFrequencies(file);
        verify(mockPwm, times(2)).on(anyInt(), anyInt());
        verify(mockPwm, times(2)).off();
    }
}