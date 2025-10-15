package com.opensourcewithslu.components.controllers;

import java.awt.Event;

import com.opensourcewithslu.inputdevices.IRObstacleAvoidanceHelper;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalState;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Controller("/ir-obstacle")
public class IRObstacleAvoidanceController {

    private final IRObstacleAvoidanceHelper irObstacleAvoidanceHelper;

    // Inject the IR obstacle avoidance digital input pin
    @Inject
    public IRObstacleAvoidanceController(@Named("ir-obstacle-input") DigitalInput digitalInput) {
        this.irObstacleAvoidanceHelper = new IRObstacleAvoidanceHelper(digitalInput);
    }

    // Endpoint to check if an obstacle is currently detected
    @Get("/status")
    public boolean isObstacleDetected() {
        return irObstacleAvoidanceHelper.isObstacleDetected();
    }

    // Endpoint to get the current state (HIGH or LOW)
    @Get("/state")
    public DigitalState getState() {
        return irObstacleAvoidanceHelper.getState();
    }

    // Endpoint to get human-readable status message
    @Get("/message")
    public String getStatusMessage() {
        return irObstacleAvoidanceHelper.getStatusMessage();
    }

    // Endpoint to wait for obstacle detection with timeout
    @Get("/wait/{timeoutMillis}")
    public boolean waitForObstacle(long timeoutMillis) throws InterruptedException {
        return irObstacleAvoidanceHelper.waitForObstacle(timeoutMillis);
    }
    // initializing endpoint
    @Get("/init")
    public void enable() {
        irObstacleAvoidanceHelper.addEventListener (event ->{
            if (irObstacleAvoidanceHelper.isObstacleDetected()){
                System.out.println("Obstacle Detected");
            } else {
                System.out.println("No Obstacle");
            }
    });
    }
}
