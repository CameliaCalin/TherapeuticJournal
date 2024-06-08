# TherapeuticJournal

TherapeuticJournal is an application that allows users to track and manage their activities, emotions, and other types of entries for the purpose of maintaining a therapeutic journal.

## Project Structure

The TherapeuticJournal project has the following directory and file structure:

[Paste your directory structure here]

## Description

The project is divided into multiple packages and modules, each with specific responsibilities:

- **api**: Contains API controllers for different entities such as activities, emotions, etc.
- **domain**: Contains JPA entity classes that are mapped to tables in the database.
- **Repository**: Contains interfaces for JPA repositories that handle data access.
- **exception**: Contains classes for handling custom exceptions.
- **resources**: Contains configuration files and static resources of the application.

## Setup

To run the TherapeuticJournal project, make sure you have Java and Maven installed on your system. You can compile and run the application using the following Maven commands:

```bash
mvn clean install
mvn spring-boot:run
