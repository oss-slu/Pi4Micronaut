package com.opensourcewithslu.outputdevices;

import com.pi4j.io.pwm.Pwm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PassiveBuzzerHelperTest {

    @TempDir
    Path tempDir;

    @Mock
    private Pwm passiveBuzzer;

    @InjectMocks
    @Spy
    private PassiveBuzzerHelper passiveBuzzerHelper;


    @Test
    void passiveBuzzerOn() {
        int passBuzzDC = 1;
        int passiveBuzzerFreq = 50;
        passiveBuzzerHelper.passiveBuzzerOn(passBuzzDC, passiveBuzzerFreq);
        verify(passiveBuzzer, times(1)).on(passBuzzDC, passiveBuzzerFreq);
    }

    @Test
    void passiveBuzzerOff() {
        passiveBuzzerHelper.passiveBuzzerOff();
        verify(passiveBuzzer, times(1)).off();
    }

    @Test
    void setFrequencies() throws IOException {
        Path path = tempDir.resolve("frequencies.txt");
        Files.writeString(path, "20,30,10000,20000,30000");

        passiveBuzzerHelper.setFrequencies(path.toFile());

        verify(passiveBuzzer, times(1)).on(passiveBuzzerHelper.passBuzzDC, 20);
        verify(passiveBuzzer, times(1)).on(passiveBuzzerHelper.passBuzzDC, 30);
        verify(passiveBuzzer, times(1)).on(passiveBuzzerHelper.passBuzzDC, 10000);
        verify(passiveBuzzer, times(1)).on(passiveBuzzerHelper.passBuzzDC, 20000);
    }

    @Test
    void passiveBuzzTone() {
        doNothing().when(passiveBuzzerHelper).passiveBuzzerOn(anyInt(), anyInt());
        doNothing().when(passiveBuzzerHelper).sleep(anyLong());
        doNothing().when(passiveBuzzerHelper).passiveBuzzerOff();

        passiveBuzzerHelper.passiveBuzzTone();

        verify(passiveBuzzerHelper, times(1))
                .passiveBuzzerOn(passiveBuzzerHelper.passBuzzDC, passiveBuzzerHelper.passiveBuzzerFreq);
        verify(passiveBuzzerHelper, times(1)).passiveBuzzerOff();
    }

    @Test
    void toneIterator() {
        doNothing().when(passiveBuzzerHelper).passiveBuzzerOn(anyInt(), anyInt());
        doNothing().when(passiveBuzzerHelper).sleep(anyLong());
        doNothing().when(passiveBuzzerHelper).passiveBuzzerOff();

        passiveBuzzerHelper.toneIterator();

        int [] frequencies  = { 880, 1760, 3520,9000,15000};
        verify(passiveBuzzerHelper, atLeast(frequencies.length)).sleep(anyLong());
        for(int i : frequencies){
            verify(passiveBuzzerHelper).passiveBuzzerOn(passiveBuzzerHelper.passBuzzDC, i);
        }
        verify(passiveBuzzerHelper, times(frequencies.length)).passiveBuzzerOff();
    }

    @Test
    void piToneSequence() {
        doNothing().when(passiveBuzzerHelper).passiveBuzzerOn(anyInt(), anyInt());
        doNothing().when(passiveBuzzerHelper).sleep(anyLong());
        doNothing().when(passiveBuzzerHelper).passiveBuzzerOff();

        passiveBuzzerHelper.piToneSequence();

        int [] digitsOfPi = {3, 1, 4, 1, 5, 9, 2, 6, 5};
        verify(passiveBuzzerHelper, times(digitsOfPi.length)).sleep(500);
        verify(passiveBuzzerHelper, times(1)).sleep(1000);
        verify(passiveBuzzerHelper, times(1)).passiveBuzzerOff();
        verify(passiveBuzzerHelper, times(digitsOfPi.length + 1))
                .passiveBuzzerOn(anyInt(), anyInt());
    }
}
