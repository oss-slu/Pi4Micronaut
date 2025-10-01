package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReedSwitchHelper {
    private static final Logger log = LoggerFactory.getLogger(ReedSwitchHelper.class);

    private final DigitalInput reedSwitchDigitalInput;

    private DigitalStateChangeListener reedSwitchChangeListener;

        /**
         * Shows if the reed switch has detected magnetic field.
         */
        public boolean isDetected;

        /**
        * ReedSwitchHelper constructor.
        * @param reedSwitchDigitalInput A Pi4J DigitalInput object.
        */
        public ReedSwitchHelper(DigitalInput reedSwitchDigitalInput)
        {
            this.reedSwitchDigitalInput = reedSwitchDigitalInput;
            this.isDetected = reedSwitchDigitalInput.isHigh();

            initialize();
        }

        /**
        * Initializes the listener that keeps track of if the reed switch has detected magnetic field or not. 
        * It is automatically called when the ReedSwitchHelper is instantiated.
        */
        private void initialize() {

            log.info("Initializing Reed Switch");

            reedSwitchChangeListener = e-> isDetected = reedSwitchDigitalInput.isHigh();
            reedSwitchDigitalInput.addListener(reedSwitchChangeListener);
        }

        /**
        * Adds an event listener to the Reed switch.
        * @param function A Pi4J DigitalStateChangeListener object.
        */
        public void addEventListener(DigitalStateChangeListener function)
        {
            reedSwitchChangeListener = function;
            reedSwitchDigitalInput.addListener(reedSwitchChangeListener);
        }

        /**
        * Removes the event listener from the Reed switch.
        */
        public void removeEventListener()
        {
            if (reedSwitchChangeListener != null) {
                reedSwitchDigitalInput.removeListener(reedSwitchChangeListener);
                reedSwitchChangeListener = null;
            }
        }
}
