## Lab_Automation
### An example project to demonstrate how to use Pi4Micronaut Library

This project is developed using kotlin along with the Micronaut application.

For Lab automation, we are going to assume the mouse maze.
The idea of this maze is to figure out how much time the mouse takes to exit the maze.
We are going to use 2 PIR Motion sensors at the entry and exit locations.
- Start and end time need to be noted based on the PIR sensor's input.
- Subtract end time with the start time to get the time taken by the mouse to exit.
- Use LCD to display messages like Mouse entered, mouse left and total time.


### Build the shadowJar file and copy it on to the Raspberry Pi to run the application

- Command to build shadowjar
---
./gradlew shadowJar
---

