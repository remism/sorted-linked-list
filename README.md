# Sorted Linked List Library #

A library that provides implementation of ordered linked list.

## Introduction ##
Sorted linked list is single-linked implementation of generic collection. List keeps stored items in order defined by
provided Comparator.

Library is intentionally compiled with JDK 11 to provide reasonable compatibility.

## Getting started ##
TBD

## Usage ##
TBD

## Build ##
Library is single module gradle project.

Use `./gradlew jar` to build the jar. Once successfully built, you can find the jar file in `./build/libs/lib.jar`

## Contributing ##
### Code style ###
Project contains reasonable coding styles default delivered via [editorconfig](https://editorconfig.org/)

### Tests ###
Library is covered with unit tests (JUnit 5). Quality gate of 80% test coverage is enforced
by [Jacoco](https://docs.gradle.org/current/dsl/org.gradle.testing.jacoco.tasks.JacocoCoverageVerification.html) (see the [./lib/build.gradle.kts](lib/build.gradle.kts)).
