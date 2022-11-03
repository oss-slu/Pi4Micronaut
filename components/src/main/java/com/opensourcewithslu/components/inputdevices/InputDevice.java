package com.opensourcewithslu.components.inputdevices;

import java.util.Collection;
import java.util.concurrent.Callable;

public abstract class InputDevice<T> {
    public abstract void initialize();

    public abstract void addEventListener(Callable<T> function);

    public abstract void removeEventListener(Callable<T> function);
}
