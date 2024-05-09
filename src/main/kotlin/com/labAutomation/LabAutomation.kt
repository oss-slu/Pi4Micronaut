package com.labAutomation

import com.opensourcewithslu.inputdevices.PIRSensorHelper
import com.opensourcewithslu.outputdevices.LCD1602Helper
import com.opensourcewithslu.outputdevices.RGBLEDHelper
import com.opensourcewithslu.utilities.MultipinConfiguration
import com.pi4j.context.Context
import com.pi4j.io.gpio.digital.DigitalInput
import com.pi4j.io.gpio.digital.DigitalStateChangeEvent
import com.pi4j.io.i2c.I2CConfig
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import jakarta.inject.Named
import org.slf4j.Logger
import org.slf4j.LoggerFactory


@Controller("/LabAutomation")
class LabAutomation(
    @Named("lcd") lcd: I2CConfig?,
    @Named("rgb-led") rgb: MultipinConfiguration?,
    @Named("pir-sensor-1") pirStart: DigitalInput?,
    @Named("pir-sensor-2") pirEnd: DigitalInput?,
    pi4j: Context?
) {
    private val lcd = LCD1602Helper(lcd, pi4j)
    private val rgb = RGBLEDHelper(rgb)
    private val pirStart = PIRSensorHelper(pirStart)
    private val pirEnd = PIRSensorHelper(pirEnd)

    private val timer: Thread
    private var inMaze: Boolean
    private var startTime: Long = 0
    private var currentTime: Long = 0
    private var endTime: Long = 0

    // Lab Automation System detects when a mouse enters and leaves a maze and
    // displays the time it takes for the mouse to complete the maze in seconds.
    init {
        this.inMaze = false

        // Timer thread: counts and displays how long the mouse has been in the maze in seconds
        this.timer = Thread {
            while (true) {
                try {
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    log.error("cant sleep :(", e)
                }

                // If the mouse is not in the maze, dont execute the rest of the while block
                if (!this.inMaze) {
                    continue
                }

                // Calculate how long the mouse has been in the maze and write to LCD display
                val newTime = System.currentTimeMillis()
                if (newTime - this.currentTime >= 1000) {
                    val displayTime = (newTime - this.startTime) / 1000
                    this.currentTime = newTime
                    this.lcd.writeTextAtLine("Current Time:", 1)
                    this.lcd.writeTextAtLine(String.format("%d seconds", displayTime), 2)
                }
            }
        }
        timer.start()
    }

    // Enables the Lab Automation system
    @Get("/enable")
    fun enable() {
        // Adds event listener for the motion sensor at the start of the maze

        pirStart.addEventListener { event: DigitalStateChangeEvent<*>? ->

            // Detects if mouse has entering the maze
            if (pirStart.isMoving && !this.inMaze) {
                this.inMaze = true
                rgb.setGreen(255)
                rgb.setRed(0)
                this.startTime = System.currentTimeMillis()
                this.currentTime = this.startTime
                lcd.clearDisplay()
            }
        }

        // Adds event listener for the motion sensor at the end of the maze
        pirEnd.addEventListener { event: DigitalStateChangeEvent<*>? ->

            // Detects if mouse has exited the maze
            if (pirEnd.isMoving && this.inMaze) {
                this.inMaze = false
                rgb.setGreen(0)
                rgb.setRed(255)
                this.endTime = System.currentTimeMillis()
                val finishTime =
                    (this.endTime - this.startTime) / 1000
                lcd.writeTextAtLine("Final Time:", 1)
                lcd.writeTextAtLine(String.format("%d seconds", finishTime), 2)
                log.info(String.format("Final Time: %d seconds", finishTime))
            }
        }
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(LabAutomation::class.java)
    }
}