package com.opensourcewithslu;

import io.micronaut.runtime.Micronaut;

/**
 * Runs the Micronaut framework in the Pi4Micronaut framework.
 */
public class Application {
    /**
     * constructor
     * @param args None
     */
    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}
