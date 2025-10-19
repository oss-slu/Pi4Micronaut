import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import com.opensourcewithslu.inputdevices.SlideSwitchHelper;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class SlideSwitchHelperTest {
    private DigitalInput slideSwitchInput;
    private SlideSwitchHelper slideSwitchHelper;

    @BeforeEach
    void setUp() {
        slideSwitchInput = mock(DigitalInput.class);
        when(slideSwitchInput.isHigh()).thenReturn(true);
        slideSwitchHelper = new SlideSwitchHelper(slideSwitchInput);
    }

    @Test
    void testIsOnFieldSetCorrectlyWhenInputIsHigh() {
        when(slideSwitchInput.isHigh()).thenReturn(true);
        slideSwitchHelper = new SlideSwitchHelper(slideSwitchInput);
        assertTrue(slideSwitchHelper.isOn, "Expected isOn to be true when DigitalInput is high");
    }

    @Test
    void testIsOnFieldSetCorrectlyWhenInputIsLow() {
        when(slideSwitchInput.isHigh()).thenReturn(false);
        slideSwitchHelper = new SlideSwitchHelper(slideSwitchInput);
        assertFalse(slideSwitchHelper.isOn, "Expected isOn to be false when DigitalInput is low");
    }

    @Test
    void testAddEventListener() {
        DigitalStateChangeListener dummyListener = mock(DigitalStateChangeListener.class);
        slideSwitchHelper.addEventListener(dummyListener);
        verify(slideSwitchInput).addListener(dummyListener);
    }

    @Test
    void testRemoveEventListener() {
        DigitalStateChangeListener dummyListener = mock(DigitalStateChangeListener.class);
        slideSwitchHelper.addEventListener(dummyListener);
        slideSwitchHelper.removeEventListener(dummyListener);
        verify(slideSwitchInput).removeListener(dummyListener);
    }

    @Test
    void testCustomListenerInvocation() {
        AtomicBoolean listenerInvoked = new AtomicBoolean(false);
        DigitalStateChangeListener listener = event -> listenerInvoked.set(true);
        slideSwitchHelper.addEventListener(listener);
        listener.onDigitalStateChange(null);
        assertTrue(listenerInvoked.get(), "The custom listener should have been invoked.");
    }

    @Test
    void testInternalListenerUpdatesIsOn() {
        ArgumentCaptor<DigitalStateChangeListener> captor = ArgumentCaptor.forClass(DigitalStateChangeListener.class);
        verify(slideSwitchInput, atLeastOnce()).addListener(captor.capture());
        DigitalStateChangeListener internalListener = captor.getAllValues().get(0);

        when(slideSwitchInput.isHigh()).thenReturn(false);
        internalListener.onDigitalStateChange(null);
        assertFalse(slideSwitchHelper.isOn, "Expected isOn to update to false after state change.");

        when(slideSwitchInput.isHigh()).thenReturn(true);
        internalListener.onDigitalStateChange(null);
        assertTrue(slideSwitchHelper.isOn, "Expected isOn to update to true after state change.");
    }

//    @Test
//    void testInitializationLogsCaptureSystemOut() {
//        PrintStream originalOut = System.out;
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream ps = new PrintStream(baos);
//        System.setOut(ps);
//        try {
//            when(slideSwitchInput.isHigh()).thenReturn(true);
//            new SlideSwitchHelper(slideSwitchInput);
//            ps.flush();
//            String output = baos.toString();
//            assertTrue(output.contains("Initializing Slide Switch"),
//                    "Expected log message 'Initializing Slide Switch' was not found in System.out output.");
//        } finally {
//            System.setOut(originalOut);
//        }
//    }
}
