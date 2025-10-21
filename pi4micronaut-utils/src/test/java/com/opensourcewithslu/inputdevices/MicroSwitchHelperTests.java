package com.opensourcewithslu.inputdevices;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import com.opensourcewithslu.mock.MockDigitalInput;
import com.pi4j.io.gpio.digital.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;


/**
 * Unit tests for the micro switch helper class
 */
public class MicroSwitchHelperTests {


    private MockDigitalInput mockDigitalInput;
    private MicroSwitchHelper microSwitchHelper;


    @BeforeEach
    public void setUp(){
        mockDigitalInput = new MockDigitalInput();
        microSwitchHelper = new MicroSwitchHelper(mockDigitalInput);
    }


    @Test
    public void testInitializationLogic(){
        //verify isPressed is initialized based on the input state
        assertFalse(microSwitchHelper.isPressed);


        //simulate high state so isPressed is triggered
        mockDigitalInput.SetState(DigitalState.HIGH);
        MicroSwitchHelper newMicroSwitchHelper = new MicroSwitchHelper(mockDigitalInput);
        assertTrue(newMicroSwitchHelper.isPressed);


    }


    @Test
    public void testAddEventListener() {
        DigitalInput mockInput = mock(DigitalInput.class);
        MicroSwitchHelper microSwitchHelper = new MicroSwitchHelper(mockInput);


        DigitalStateChangeListener dummyListener = mock(DigitalStateChangeListener.class);


        microSwitchHelper.addEventListener(dummyListener);
        verify(mockInput).addListener(dummyListener);
    }

    @Test
    public void testRemoveEventListener(){
        DigitalInput mockInput = mock(DigitalInput.class);
        MicroSwitchHelper helper = new MicroSwitchHelper(mockInput);


        DigitalStateChangeListener dummyListener = mock(DigitalStateChangeListener.class);
        helper.addEventListener(dummyListener);


        helper.removeEventListener();
        verify(mockInput).removeListener(dummyListener);
    }


    @Test
    public void testStateUpdate(){
        //starting with state at low
        mockDigitalInput.SetState(DigitalState.LOW);
        MicroSwitchHelper helperLow = new MicroSwitchHelper(mockDigitalInput);
        assertFalse(helperLow.isPressed);


        //switching to high
        mockDigitalInput.SetState(DigitalState.HIGH);
        MicroSwitchHelper helperHigh = new MicroSwitchHelper(mockDigitalInput);
        assertTrue(helperHigh.isPressed);


        //switching state back to low
        mockDigitalInput.SetState(DigitalState.LOW);
        MicroSwitchHelper helperLowAgain = new MicroSwitchHelper(mockDigitalInput);
        assertFalse(helperLowAgain.isPressed);
    }


    @Test
    void testLogsOnInitialize() {
        DigitalInput mockInput = mock(DigitalInput.class);
        Logger loggerMock = mock(Logger.class);


        MicroSwitchHelper helper = new MicroSwitchHelper(mockInput);
        helper.setLog(loggerMock);
        helper.initialize();


        verify(loggerMock).info("Initializing Micro Switch");
    }


    @Test
    public void testRemoveEventListenerNoEventListener(){
        DigitalInput mockInput = mock(DigitalInput.class);
        MicroSwitchHelper helper = new MicroSwitchHelper(mockInput);
        // no call to add event listener


        //remove event listener and verify that it was called properly
        helper.removeEventListener();
        verify(mockInput).removeListener(any(DigitalStateChangeListener.class));
    }
}