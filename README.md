SafetyNet Alerts
SafetyNetAlerts is a Spring Boot application that provides alerts and information about people based on their medical records and the fire stations that cover their area. 
The application is designed following the principles of the Model-View-Controller (MVC) architecture and adheres to SOLID principles.
And it uses Java to run and stores data in JSON files.

Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

Prerequisites
What you need to install the software and how to install them:

Java 17
Maven 3.6.2

Download Java here
https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html


Install Java
https://docs.oracle.com/en/java/javase/17/install/overview-jdk-installation.html

Install Maven 
https://maven.apache.org/install.html

Running the App
Post installation of Java and Maven, you will need to set up your development environment. 

Follow these steps:

Clone the repository
Import the projet to your IDE
Build the project with maven commands : mvn clean install

You can run SafetyNetAlerts by using maven commands: mvn spring-boot:run

Testing
The app has unit tests and integration tests written. 
You can run them with maven commands
mvn test for unit tests
mvn verify for integration tests

Usage
You can interact with the application using various HTTP methods via Postman or any other API testing tool. 

This README provides a comprehensive guide on how to get started with the SafetyNetAlerts application, including prerequisites, installation steps, running the app and testing.
