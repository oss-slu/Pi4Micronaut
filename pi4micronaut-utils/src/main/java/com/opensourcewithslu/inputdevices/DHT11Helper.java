package com.opensourcewithslu.inputdevices;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DHT11Helper {

    private static final Logger log = LoggerFactory.getLogger(DHT11Helper.class);

    private final DigitalInput dht11Input;

    public float temperature;

    public float humidity;

    private final int[] byteStream = new int[5];

    public DHT11Helper(DigitalInput sensorInput)
    {
        this.dht11Input = sensorInput;

        initialize();
    }

    public void initialize()
    {
        log.info("Initializing DHT11 Sensor");

        dht11Input.addListener(e -> {
            if(e.state() == DigitalState.LOW)
            {
                return;
            }

            int j = 0;
            for (int i = 0; i < byteStream.length; i++)
            {
                int counter = 0;
                while (dht11Input.isLow())
                {
                    counter++;
                    if (counter == 255)
                        break;
                }

                byteStream[i] = counter;

                counter = 0;
                while (dht11Input.isHigh())
                {
                    counter++;
                    if (counter == 255)
                        break;
                }

                for (int k = 0; k < 8; k++)
                {
                    counter = 0;
                    while (dht11Input.isLow())
                    {
                        counter++;
                        if (counter == 255)
                            break;
                    }

                    byteStream[j] <<= 1;
                    if (counter > 16)
                        byteStream[j] |= 1;

                    counter = 0;
                    while (dht11Input.isHigh())
                    {
                        counter++;
                        if (counter == 255)
                            break;
                    }

                    if (counter == 255)
                        break;
                }
                j++;
            }

            if ((j >= 40) && (byteStream[4] == ((byteStream[0] + byteStream[1] + byteStream[2] + byteStream[3]) & 0xFF)))
            {
                log.info("Successfully read data from DHT11 sensor");

                humidity = byteStream[0];
                temperature = byteStream[2];
            }
            else
                log.info("Failed to read data from DHT11 sensor");
        });
    }
}
