import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import com.opensourcewithslu.inputdevices.PIRSensorHelper;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class PIRSensorHelperTest {
    private DigitalInput pirSensorInput;
    private PIRSensorHelper pirSensorHelper;

    @BeforeEach
    void setUp() {
        pirSensorInput = mock(DigitalInput.class);
        when(pirSensorInput.isHigh()).thenReturn(true);
        pirSensorHelper = new PIRSensorHelper(pirSensorInput);
    }

    @Test
    void testIsMovingFieldSetCorrectlyWhenInputIsHigh() {
        when(pirSensorInput.isHigh()).thenReturn(true);
        pirSensorHelper = new PIRSensorHelper(pirSensorInput);
        assertTrue(pirSensorHelper.isMoving, "Expected isMoving to be true when PIR sensor detects motion (DigitalInput is high)");
    }

    @Test
    void testIsMovingFieldSetCorrectlyWhenInputIsLow() {
        when(pirSensorInput.isHigh()).thenReturn(false);
        pirSensorHelper = new PIRSensorHelper(pirSensorInput);
        assertFalse(pirSensorHelper.isMoving, "Expected isMoving to be true when PIR sensor detects motion (DigitalInput is low)");
    }

//    @Test
//    void testInitializationLogsCaptureSystemOut() {
//        PrintStream originalOut = System.out;
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream ps = new PrintStream(baos);
//        System.setOut(ps);
//        try {
//            when(pirSensorInput.isHigh()).thenReturn(true);
//            new PIRSensorHelper(pirSensorInput);
//            ps.flush();
//            String output = baos.toString();
//            assertTrue(output.contains("Initializing PIR Sensor"),
//                    "Expected log message 'Initializing PIR Sensor' was not found in System.out output.");
//        } finally {
//            System.setOut(originalOut);
//        }
//    }

    @Test
    void testAddEventListener() {
        DigitalStateChangeListener dummyListener = mock(DigitalStateChangeListener.class);
        pirSensorHelper.addEventListener(dummyListener);
        pirSensorHelper.removeEventListener();
        verify(pirSensorInput).removeListener(dummyListener);
    }

    @Test
    void testCustomListenerInvocation() {
        AtomicBoolean listenerInvoked = new AtomicBoolean(false);
        DigitalStateChangeListener listener = event -> listenerInvoked.set(true);
        pirSensorHelper.addEventListener(listener);
        listener.onDigitalStateChange(null);
        assertTrue(listenerInvoked.get(), "The custom listener should have been invoked.");
    }

    @Test
    void testInternalListenerUpdatesIsMoving() {
        ArgumentCaptor<DigitalStateChangeListener> captor = ArgumentCaptor.forClass(DigitalStateChangeListener.class);
        verify(pirSensorInput, atLeastOnce()).addListener(captor.capture());
        DigitalStateChangeListener internalListener = captor.getAllValues().get(0);

        when(pirSensorInput.isHigh()).thenReturn(false);
        internalListener.onDigitalStateChange(null);
        assertFalse(pirSensorHelper.isMoving, "Expected isMoving to update to false after state change.");

        when(pirSensorInput.isHigh()).thenReturn(true);
        internalListener.onDigitalStateChange(null);
        assertTrue(pirSensorHelper.isMoving, "Expected isMoving to update to true after state change.");
    }

    @Test
    void testMultipleRemoveEventListenerCalls() {
        pirSensorHelper.removeEventListener();
        pirSensorHelper.removeEventListener();
        verify(pirSensorInput, times(1)).removeListener(any());
    }

    @Test
    void testAddEventListenerOverridesExistingListener() {
        DigitalStateChangeListener firstListener = mock(DigitalStateChangeListener.class);
        DigitalStateChangeListener secondListener = mock(DigitalStateChangeListener.class);

        pirSensorHelper.addEventListener(firstListener);
        pirSensorHelper.addEventListener(secondListener);

        ArgumentCaptor<DigitalStateChangeListener> captor = ArgumentCaptor.forClass(DigitalStateChangeListener.class);
        verify(pirSensorInput, atLeast(2)).addListener(captor.capture());

        DigitalStateChangeListener lastAdded = captor.getAllValues().get(captor.getAllValues().size() - 1);
        assertSame(secondListener, lastAdded, "The new listener should override the previous listener.");

        pirSensorHelper.removeEventListener();
        verify(pirSensorInput, times(1)).removeListener(secondListener);

    }

}