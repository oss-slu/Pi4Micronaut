package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalStateChangeListener;

import java.util.Collection;
import java.util.concurrent.Callable;

public abstract class InputDevice {
    public abstract void initialize();

    public abstract void addEventListener(DigitalStateChangeListener function);

    public abstract void removeEventListener(DigitalStateChangeListener function);
}
