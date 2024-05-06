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

Pi4Micronaut is a Java library designed specifically to support researchers in integrating various sensors and electronic 
components into their research instruments and experiments. This library empowers researchers to create Internet of Things
(IoT) applications that leverage the versatility and affordability of the Raspberry Pi, enabling them to build sophisticated
research instruments tailored to their specific needs. By running directly on the Raspberry Pi platform, Pi4Micronaut serves
as a bridge between the high-level Micronaut framework and the low-level hardware control provided by Pi4J.



The Micronaut framework, widely used for building web applications and microservices, handles HTTP requests. 
These requests are then passed to Micronaut's application controller, which is custom code written for each specific web
application. The application controller can make calls to the Pi4Micronaut library to interact with the hardware components.
Pi4Micronaut library uses Pi4J for low-level hardware interaction. Thus, Pi4Micronaut provides an abstraction layer between
Pi4J utilities and Micronaut framework, allowing the developer to focus on the application logic.



# Statement of Need

Data gather is a major element of research. Many research instruments can be simply connected to a personal computer and
used as is. However, there are many circumstances when customized instruments need to be developed. The single board computer Raspberry Pi with its ability to interface with almost anything provides an answer. The small
size and versatility at a low price, makes the Raspberry Pi an ideal solution for researchers. Applications for the
Raspberry Pi include rat licking behavior @Longley, greenhouse gas effect monitoring system @Shah,
and many other applications @Jolles. Pi4Micronaut also allows for multiple users to read input from sensors. This
eliminates the elbowing that may occur when multiple researchers are trying to read input from a sensors. The utilization
of a Raspberry Pi, eliminates the hassle and cost of needing a full computer for one small sensor. 



# Availability and Usage

* [Pi4Micronaut library package](https://central.sonatype.com/artifact/io.github.oss-slu/pi4micronaut-utils).
* [Repository](https://github.com/oss-slu/Pi4Micronaut)
* [Documentation](https://oss-slu.github.io/Pi4Micronaut/).

A [check-in system](https://github.com/oss-slu/SLU_OSS_CheckIn) and a simple [security system](https://github.com/oss-slu/Pi4Micronaut/tree/Home_Automation) were created as examples for how to use this library. 

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
previous team who set the groundwork for us and set us up for the successful release of 1.0. A big thank you to 
Jeff Brown from the Unity Foundation for overseeing and supporting this project. A final thank you to Open Source with SLU for funding and supporting the project.


# References
