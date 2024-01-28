# Sorted Linked List Library #

A library that provides implementation of ordered linked list.

## Introduction ##
Sorted linked list is single-linked implementation of generic collection. List keeps stored items in order defined by
provided Comparator. As long as provided comparator is able to compare null, list allows to store nulls.

Library is intentionally compiled with JDK 11 to provide reasonable compatibility.

## Getting started ##
Put the final `lib.jar` on your classpath.

You can also build and publish to you maven local using `publishToMavenLocal`. Then add the library to your project
dependencies: `implementation "demo.sm.sdk:linked-list:VERSION"`

## Usage ##
Create new instance of collection using your own comparator, see the examples: 

- for integers
`new SortedLinkedList<>(Integer::compare);`
- allowing nulls
`new SortedLinkedList<>(Comparator.nullsLast(Integer::compareTo));`
- alphabetical order for Strings
```Java
    Collator collator = Collator.getInstance(Locale.forLanguageTag("cs_CZ"));
    SortedLinkedList<String> list = new SortedLinkedList<>(collator::compare);
```

You can then use standard Java API of `java.util.Collection` respective `java.util.Queue`

## Build ##
Library is single module gradle project.

Use `./gradlew jar` to build the jar. Once successfully built, you can find the jar file in `./build/libs/lib.jar`

## Contributing ##
### Code style ###
Project contains reasonable coding styles default delivered via [editorconfig](https://editorconfig.org/)

### Tests ###
Library is covered with unit tests (JUnit 5). Quality gate of 80% test coverage is enforced
by [Jacoco](https://docs.gradle.org/current/dsl/org.gradle.testing.jacoco.tasks.JacocoCoverageVerification.html) (see the [./lib/build.gradle.kts](lib/build.gradle.kts)).
