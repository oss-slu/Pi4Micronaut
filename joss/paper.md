---
title: 'Pi4Micronaut'
tags:
  - Java
  - Micronaut
  - RaspberryPi
  - Pi4j
  - GPIO
authors:
- name: Adrian Swindle
  equal-contrib: true
  affiliation: 1
- name: Ruthvik Mannem
  equal-contrib: true
  affiliation: 1
affiliations:
  - name: Open Source with SLU
    index: 1


date: 3 April 2024 <!-- Change for submission date? -->
bibliography: paper.bib
---

# Summary

Pi4Micronaut is an innovative Java library crafted for researchers who aim to create Internet of Things (IoT) 
applications for their research leveraging the Raspberry Pi platform. This software is designed to run directly on the 
Raspberry Pi, providing an interface between the high-level Micronaut framework and the low-level hardware control 
provided by Pi4J. It serves as a vital tool for researchers who wish to use various sensors and electronic components 
in research. By abstracting the complexity of hardware interactions, Pi4Micronaut allows developers to focus on crafting
logic and features, making it easier to bring IoT applications from concept to deployment rapidly.

Pi4Micronaut's utilization of the Micronaut framework allows for HTTP requests to interact with the software. These 
requests then can be handled application's controller which can use the helper classes provided by Pi4Micronaut. These
helper classes then interact with the connected hardware components by utilizing Pi4J's utilities. By providing the 
abstraction between the utilities of Pi4J and the developer, the developer will have less to worry during development.
Pi4Micronaut being built on Micronaut, allows for the easy creation of a server-like application that handles HTTP requests. 
These requests can come from anyone on the same Wi-Fi network. This allows for multiple people to interact with the instruments
that are connected to the Raspberry Pi. For a developer, Pi4Micronaut fills the need of a Java framework for IoT applications.




# Statement of Need

For researchers, a major component of their work is data gathering. While there are many instances when they can simply
plug the tools they are using into their personal computer, circumstances call for a more mobile or collaborative medium.
The single board computer Raspberry Pi with its ability to interface with almost anything provides an answer. The small
size and versatility at a low price, makes the Raspberry Pi an ideal solution for researchers. Applications for the
Raspberry Pi include rat licking behavior @Longley, greenhouse gas effect monitoring system @Shah,
and many other applications @Jolles. Pi4Micronaut also allows for multiple users to read input from sensors. This
eliminates the elbowing that may occur when multiple researchers are trying to read input from a sensors. The utilization
of a Raspberry Pi, eliminates the hassle and cost of needing a full computer for one small sensor. 



# Availability and Usage

The Pi4Micronaut library package can be found [here](https://central.sonatype.com/artifact/io.github.oss-slu/pi4micronaut-utils).
The repository can be found [here](https://github.com/oss-slu/Pi4Micronaut). How to use Pi4Micronaut can be found in the documentation [here](https://oss-slu.github.io/Pi4Micronaut/).


# Library

The following is the list of the currently supported hardware components:
* Push Button
* Slide Switch
* Rotary Encoder
* RFID Scanner
* LED
* RGB LED
* LCD Screen
* Photosensor
* Touch Switch Sensor
* Active Buzzer
* Passive Buzzer
* PIR Motion Sensor
* Ultrasonic Sensor


# Acknowledgments

Thank you to Alex Delgado, Joe Folen, and John Yanev for helping create the first full release of Pi4Micronaut. To the 
previous team who set the groundwork for us and set us up for the successful release of 1.0. A big thank you to Jeff Brown from the Unity Foundation for overseeing and supporting this project.


# References
