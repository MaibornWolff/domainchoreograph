package de.maibornwolff.domainchoreograph.examples.helloworld;

import de.maibornwolff.domainchoreograph.core.api.DomainChoreography;

@DomainChoreography
public interface MyChoreography {
  HelloWorld calculate(Name name);
}
