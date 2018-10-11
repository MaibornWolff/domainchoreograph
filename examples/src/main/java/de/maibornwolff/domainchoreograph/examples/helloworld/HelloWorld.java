package de.maibornwolff.domainchoreograph.examples.helloworld;

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition;
import de.maibornwolff.domainchoreograph.core.api.DomainFunction;

@DomainDefinition
public class HelloWorld {

    private String message;

    public HelloWorld(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @DomainFunction
    public static HelloWorld resolveHelloWorld(Name name) {
        return new HelloWorld("Hello " + name.getValue());
    }

    @Override
    public String toString() {
        return message;
    }
}
