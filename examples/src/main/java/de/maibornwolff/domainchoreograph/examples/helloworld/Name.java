package de.maibornwolff.domainchoreograph.examples.helloworld;

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition;

@DomainDefinition
public class Name {

    private String value;

    public Name(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
