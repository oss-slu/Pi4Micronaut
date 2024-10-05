package com.opensourcewithslu.outputdevices;
import com.pi4j.io.pwm.Pwm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

class FanHelperTest {


    @Mock
    private Pwm pwm;  // Mock Pwm class

    private FanHelper fanhelper;

    private AutoCloseable mocks;
    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        pwm = mock(Pwm.class);
        fanhelper = new FanHelper(pwm);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void testStartFan() {
        // Call the start() method
        fanhelper.start();

        // Verify that pwm.on(1024) is called to start the fan at full speed
        verify(pwm, times(1)).on(1024);

        // Verify that pwm.off() is called to stop the fan after 10 seconds
        verify(pwm, times(1)).off();
    }

    @Test
    void testStopFan() {
        // Call the stop() method
        fanhelper.stop();

        // Verify that pwm.off() is called
        verify(pwm, times(1)).off();
    }

    @Test
    void testSetFanSpeed() {
        int speed = 512;

        // Call the setSpeed() method
        fanhelper.setSpeed(speed);

        // Verify that pwm.on(speed) is called with the correct argument
        verify(pwm, times(1)).on(speed);
    }

}