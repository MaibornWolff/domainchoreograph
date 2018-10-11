# DomainChoreograph

[![Build Status](https://travis-ci.org/MaibornWolff/domainchoreograph.svg?branch=master)](https://travis-ci.org/MaibornWolff/domainchoreograph)

*DomainChoreograph* is a java library to describe, visualize and debug business algorithms in a declarative way. 

## Getting started

Let's start with setting up the dependencies.

### Gradle/Maven

*coming soon*

### Hello World
Let's write a *Hello World* program next.

```java
@DomainDefinition
public class Name {

    private String value;

    public Name(String value) {
        this.value = value;
    }

    public String getName() {
        return value;
    }
    
    @Override
    public String toString() {
        return value;
    }
}
```

```java
import de.maibornwolff.domainchoreograph.core.api.DomainFunction;

@DomainDefinition
public class HelloWorld {

    private String message;

    public HelloWorld(String message) {
        this.message = message;
    }

    public String getHelloWorld() {
        return message;
    }
    
    @DomainFunction
    public static HelloWorld calculateHelloWorld(Name name) {
        return new HelloWorld("Hello " + name.getValue());
    }
}
```

```java
import de.maibornwolff.domainchoreograph.core.api.Choreography;

public class Application {

    public static void main(String[] args) {
        
        // start Debug server programatically (optional, just to get started)
        Devtool devtool = new Devtool();
        devtool.getServer().start();

        Name name = new Name("Bob");
        
        HelloWorld helloWorld = Choreography
                .basedOnValues(name)
                .usingMiddleware(devtool.getLogger())   // this will ship the result to the running debug server (optional)
                .calculate(HelloWorld.class);

        System.out.println(helloWorld.getMessage()); 
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

A *Domain Function* represents the algorithm of a *Domain Definition*. It's supposed to be *pure* which means the method has no side effects and the result depends on method parameters only. The following constraints have to remain true:
* the method annotated by `@DomainFunction` needs to be static.
* there can only be one method in a class annotated by `@DomainFunction`.
* if a class has a method annotated by `@DomainFunction` the class itself needs to be annotated by `@DomainDefinition`.

If side effects are needed, for example to write or read something to a database, use *domain services* (see below).

#### Method Parameters of a Domain Function
The method parameters of a domain function are *domain types* identified by *domain definitions*. If the provided *domain type* is a *domain definition*, no further specification is needed. If on the other hand the *domain type* is not a *domain definition* the parameter needs to be annotated by `@DefinedBy(SomeDomainDefinition.class)` whereas `SomeDomainDefinition.class` is a class annotated by `@DomainDefinition` and has a *domain function* which returns the *domain type*.

By specifying parameters you can accidentally build cycles in the resulting dependency graph. That does not make any sense and is of course not allowed. The resulting dependency graph needs to be acyclic. 

#### Return Type of a Domain Function
The return type of a domain function is called *domain type* and **can** but not necessarily **is** a *domain definition type*.

### Domain Type
A *domain type* is any class that represents a data structure specific for the domain at hand. A *domain type* can of course also be a primitive or domain agnostic type (e.g. String, Date, ...) but it is not encouraged due to encapsulation reasons.

### Domain Object
A *domain object* is an instance of a *domain type* and represents a value that is used as input or result of the specified choreography. 

### Domain Service
...

### Choreography

The result of defining an bunch of *domain definitions* is a directed acyclic graph (DAG) whereas *domain definitions* represent the set of nodes. Ingoing edges of each node are defined by parameters of the *domain function*. 
In order to actually calculate something we need to state what we want to calculate based on what. That statement we call *choreography* and is specified by a fluent API.
  
## Other Information
* [License](./LICENSE)
