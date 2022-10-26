package com.opensourcewithslu.components.outputdevices.led;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.micronaut.core.annotation.Introspected;

import java.util.Date;

@Introspected
public class Darkness {
    private final int value;
    private final Date time;

    public Darkness(int value) {
        this.value = value;
        time = new Date();
    }

    public int getValue() {
        return value;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss zzz")
    public Date getTime() {
        return time;
    }
}
