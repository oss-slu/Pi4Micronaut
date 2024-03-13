package com.opensourcewithslu;

import io.micronaut.runtime.Micronaut;

/**
 * Runs the Micronaut framework in the Pi4Micronaut framework.
 */
public class Application {
    /**
     * This is the default constructor for the Application class.
     * It initializes the application with default settings.
     */
    public Application() { }

    /**
     * The main entry point of the application.
     *
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}
