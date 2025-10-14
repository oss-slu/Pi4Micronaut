# Contribute to the Pi4Micronaut Library

## Get Familiar with the Library

* Before making contributions, understand the purpose and functionality of the Pi4Micronaut library.

* Review the library documentation, any related articles, or tutorials.

    Our documentation can be found online at [oss-slu.github.io/Pi4Micronaut](https://oss-slu.github.io/Pi4Micronaut/). Good information and tutorials on Raspberry Pis and electronic components can be found online from [Sunfounder](https://docs.sunfounder.com/en/latest/), [SparkFun](https://learn.sparkfun.com/), or others.

    This file will go over some quick information about our library, but please refer to our documentation webpage for more complete information.

## Set Up Your Development Environment

* Fork the library’s repository from the GitHub.

* Clone your fork locally.

* Follow setup instructions provided in the repository’s README or our online documentation.

## Understand the Contribution Process

* Familiarize yourself with the library’s contribution guidelines.

* Understand the [community guidelines](community-guidelines.md).

* Find out the preferred method of communication (e.g., issues, mailing list, discord).

## Identify a Way to Contribute

* Bug fixes: Look for open issues tagged as 'bug' or report new ones.

* New features: Discuss new ideas before implementing, to gauge interest and get guidance.

* Documentation: Contribute to the README, ADOC or other documentation.

* Testing: Improve or expand the test suite.

* Refactoring: Optimize existing code or improve its readability.

## Making Changes

* Always create a new branch for your changes.

* Follow the library’s coding style and standards.

* Write clean, well-documented code.

* Add or update tests for your changes, if necessary.

* Commit frequently with meaningful commit messages.

## Test Your Changes

* Ensure that all tests pass.

* Manually test your changes for unforeseen issues.

* Ensure your changes do not introduce regressions.

* Use your own hardware to test the new component integration.

* Note: A test suite will be developed in future to test the components without the use of external hardware

## Signing the Contributor License Agreement

* While creating a pull request, you’ll be prompted to sign a Contributor License Agreement. Please do so by logging in with your GitHub account.

## Submit a Pull Request (PR)

* Push your changes to your forked repository. Create a pull request from your branch to the main library’s main branch.

* In the PR description, explain your changes, motivations, and any decisions made.

* Link to any related issues or discussions.

## Respond to Feedback

* Maintainers or other contributors might provide feedback. Be open to suggestions and make necessary revisions.

* Engage in a constructive dialogue to ensure the quality of the contribution.

## Stay Updated

* Keep your fork synchronized with the main repository to ease future contributions.

* Regularly check for updates or changes in the library’s contribution guidelines.

## Engage with the Community

* Attend community meetings or join chat groups.

* Help other contributors or users when you can.

* Note: While your contribution is highly valued, there’s no guarantee that all pull requests will be merged. It depends on the library’s direction, quality of the contribution, and decisions of the maintainers.

Thanks for considering a contribution to the Pi4Micronaut library! Your involvement helps make the project better for everyone.


### 3.1. How to Create a New Component

If its compatible with a Raspberry Pi then it should work well with the Pi4Micronaut. The following steps should encompass how most components are added to the library. Start by creating a new Issue to suggest changes.

## Determine the communication type for the component which you want to use. For example, Buzzer works with PWM and LCD1602 works with I2C.

## Set up the circuit

## Add Component to the Application yml

* The new component will need to be added to the application yml found at components/src/main/resources/application.yml.

* More information on the application.yml found in Communicating with a Hardware Component

## Create a Helper

* A Helper is what the Controller calls to do an action. For example, to change the color of an RGB LED the controller will take the request to change it. The Controller will then call the change color method in the helper. The helper then takes all the actions needed to change the color of the LED.

* See the RBG Helper for an example of a Helper.

* All Helpers should be kept here: pi4micronaut-utils\src\main\java\com\opensourcewithslu\(inputdevices or outputdevices)

## Create a Controller

* Controllers define and handle interactions with a given component. The Controller of a component will have a @Controller("/example") right above the class declaration that acts as the endpoint for requests to the component. Instead of "example", you should name the endpoint something that is identifiable to the component. Each method of the Controller should have a @Get("/exampleEndPoint") above the method declaration. The endpoint for the method should have the same name as the method and any parameters should be included in the endpoint /exampleEndPoint/{parameter1},{parameter2}.

* See the RGB Controller for an example of a Controller.

* Consult the Micronaut Documentation for more explanation on Controllers.

* All Controllers should be kept here: components\src\main\java\com\opensourcewithslu\components\controllers

## Thoroughly test

* Contributors should thoroughly test their integrations

* When submitting a pull request, make sure to include how you tested the component, any circuits that you may have used, and how to run any examples you may have created.

* It is important that reviewers are able to replicated your work in order to properly test the implementation.

## Create documentation for the component

* Create an .adoc file with the component name as the file name.

* Make sure to include all the information that the other components. Simply copy/paste an existing components documentation and edit as needed.

* Add the file here: pi4micronaut-utils/src/docs/asciidoc/components under either input or output components.
