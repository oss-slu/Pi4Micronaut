package com.opensourcewithslu.inputdevices;
import com.pi4j.common.Metadata;
import com.pi4j.context.Context;
import com.pi4j.exception.InitializeException;
import com.pi4j.exception.ShutdownException;
import com.pi4j.io.binding.DigitalBinding;
import com.pi4j.io.gpio.digital.*;

public class MockDigitalInput implements DigitalInput {
    DigitalState mockState;
    public MockDigitalInput(){
        mockState = DigitalState.UNKNOWN;
    }
    public void SetState(DigitalState fakeState) {
        mockState = fakeState;
    }
    @Override
    public DigitalState state() {
        return mockState;
    }

    @Override
    public DigitalInput addListener(DigitalStateChangeListener... digitalStateChangeListeners) {
        return null;
    }

    @Override
    public DigitalInput removeListener(DigitalStateChangeListener... digitalStateChangeListeners) {
        return null;
    }

    @Override
    public boolean isOn() {
        return false;
    }

    @Override
    public DigitalInput bind(DigitalBinding... digitalBindings) {
        return null;
    }

    @Override
    public DigitalInput unbind(DigitalBinding... digitalBindings) {
        return null;
    }

    @Override
    public DigitalInputConfig config() {
        return null;
    }

    @Override
    public DigitalInput name(String s) {
        return null;
    }

    @Override
    public DigitalInput description(String s) {
        return null;
    }

    @Override
    public DigitalInputProvider provider() {
        return null;
    }

    @Override
    public String id() {
        return "";
    }

    @Override
    public String name() {
        return "";
    }

    @Override
    public String description() {
        return "";
    }

    @Override
    public Metadata metadata() {
        return null;
    }

    @Override
    public Object initialize(Context context) throws InitializeException {
        return null;
    }

    @Override
    public Object shutdown(Context context) throws ShutdownException {
        return null;
    }
}
