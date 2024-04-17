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
- name: Alex Delgado
  equal-contrib: true
  affiliation: 1
- name: Joe Folen
  equal-contrib: true
  affiliation: 1
- name: John Yanev
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


<!-- Should use this: https://joss.theoj.org/papers/10.21105/joss.02584 as an example. -->

[//]: # (We need to connect every thing to Pi4Micronaut being used for research purposes. )

# Statement of Need

For researchers, a major component of their work is data gathering. While there are many instances when they can simply
plug the tools they are using into their personal computer, circumstances call for a more mobile or collaborative medium.
The single board computer Raspberry Pi with its ability to interface with almost anything provides an answer. The small
size and versatility at a low price, makes the Raspberry Pi an ideal solution for researchers. Applications for the
Raspberry Pi include rat licking behavior ([Longley, et al.](#References)), greenhouse gas effect monitoring system ([Shah and Bhatt](#References)),
and many other applications([Jolles](#References)). Pi4Micronaut also allows for multiple users to read input from sensors. This
eliminates the elbowing that may occur when multiple researchers are trying to read input from a sensors. The utilization
of a Raspberry Pi, eliminates the hassle and cost of needing a full computer for one small sensor. 

<!-- Find papers that use raspberry pi's with instruments that could in theory work with the Pi4Micronaut library. -->

# Availability and Usage

The Pi4Micronaut library package can be found [here](https://central.sonatype.com/artifact/io.github.oss-slu/pi4micronaut-utils).
The repository can be found [here](https://github.com/oss-slu/Pi4Micronaut). How to use Pi4Micronaut can be found in the documentation [here](https://oss-slu.github.io/Pi4Micronaut/).


# Library

The following is the list of the currently supported hardware:
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



# References <!-- All this info needs to go in a .bib file-->

<!-- All the citations should be something that could be reasonably used with Pi4Micronaut. -->

Jolles, Jolle W. “Broad‐scale applications of the Raspberry Pi: A Review and guide for Biologists.” Methods in Ecology and Evolution, vol. 12, no. 9, 23 June 2021, pp. 1562–1579, https://doi.org/10.1111/2041-210x.13652.
<!-- Jolles list out a bunch of research applications of Raspberry Pis. -->

Longley, Matthew, et al. “An open source device for operant licking in rats.” PeerJ, vol. 5, 14 Feb. 2017, https://doi.org/10.7717/peerj.2981.
<!-- Longley used a touch sensor, which is something we have implemented.  -->

Shah, Neel P., and Priyang Bhatt. "Greenhouse automation and monitoring system design and implementation." International journal of advanced research in computer science 8.9 (2017): 468-471. http://dx.doi.org/10.26483/ijarcs.v8i9.4981
<!-- Shah and Bhatt used a humidity sensor (along with other things) -->

