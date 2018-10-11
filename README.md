# DomainChoreograph

[![Build Status](https://travis-ci.org/MaibornWolff/domainchoreograph.svg?branch=master)](https://travis-ci.org/MaibornWolff/domainchoreograph)

*DomainChoreograph* is a kotlin library to describe, visualize and debug business algorithms in a declarative way. 

## Getting started

Let's start with setting up the dependencies.

### Gradle/Maven

*coming soon*

### Hello World
Let's write a *Hello World* program next.

```kotlin
@DomainDefinition
data class Name (val value: String)
```

```kotlin
import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction

@DomainDefinition
data class HelloWorld(val message: String) {

    companion object {

        @DomainFunction
        fun resolveHelloWorld(name: Name): HelloWorld {
            return HelloWorld("Hello " + name.value)
        }
    }
}
```

```kotlin
import de.maibornwolff.domainchoreograph.core.api.DomainEnvironment
import de.maibornwolff.domainchoreograph.domainanalytics.DomainAnalytics

object Application {

    @JvmStatic
    fun main(args: Array<String>) {

        // start analytics server
        val domainAnalytics = DomainAnalytics()
        domainAnalytics.server.start()

        // setup environment
        val environment = DomainEnvironment.builder()
            .addLogger(domainAnalytics.logger)
            .build()

        // input parameter
        val name = Name("Bob")

        // get generated implementation
        val choreography: HelloWorldChoreography = environment.get()
        val helloWorld = choreography.calculate(name)

        println(helloWorld.message)
    }
}
```

Output on console:
```
Start debug server on port 5400
Hello Bob
```

What your browser is showing:

![Debug-View in Browser](media/hello-world-browseroutput-1.png)

## Some terminology

In order to explain the underlying concepts and to describe detailed usage of the library, let's agree on some terminology first.

### Domain Definition

*Domain Definitions* are the building blocks of the domain algorithm to be described. A domain definition is represented by a class annotated by `@DomainDefinition` and is called *domain definition type*.
It uniquely identifies a *domain object* in a given context. Furthermore, an algorithm can be provided (see `@DomainFunction`) that calculates the object based on other domain objects (again identified by domain definitions). 

### Domain Function

A *domain function* represents the algorithm of a *domain definition*. It's supposed to be _pure_ which means the method has no side effects and the result depends on method parameters only. The following constraints have to remain true:
* the method annotated by `@DomainFunction` needs to be defined in a companion object (think of it as some sort of factory method).
* there can only be one method in a class annotated by `@DomainFunction`.
* if a class has a method annotated by `@DomainFunction` the class itself needs to be annotated by `@DomainDefinition`.

If side effects are needed, for example to write or read something to/from a database, use *domain services* (see below).

#### Method Parameters of a Domain Function
The method parameters of a domain function are either *domain definition types* or *domain service types*.

By specifying parameters you can accidentally build cycles in the resulting dependency graph. That does not make any sense and is of course prohibited. The resulting dependency graph needs to be _acyclic_. 

#### Return Type of a Domain Function
A *domain function* return the *domain definition type* the domain function is defined in.

### Domain Definition Object
A *domain definition object* is an instance of a *domain definition type* and represents a value that is used as input or result of a choreography. 

### Domain Service
A *domain service* is similar to a *domain definition* but is not going to be serialized to during process visualization.

### Domain Service Object
A *domain service object* is an instance of a *domain service type* and represents a service that is used in domain functions. 

### Choreography

The result of defining an bunch of domain definitions is a directed acyclic graph (DAG) whereas *domain definition types* represent the set of nodes. Ingoing edges of each node are defined by parameters of the *domain function*. In order to actually calculate something we need to state what we want to calculate based on what. That statement we call *choreography* and is defined by an interface annotated by `@DomainChoreography`.

A corresponding implementation of that interface is _generated_ at compile time and can be obtained by a previously configured *domain environment* (see example above). It can also be stated as dependency in a domain definition. That way *sub choreographies* are made possible.

### Domain Environment

A *domain environment* is used to obtain generated implementations of a choreography. You can also configure logging plugins for different purposes.
  
## Other Information
* [License](./LICENSE)
