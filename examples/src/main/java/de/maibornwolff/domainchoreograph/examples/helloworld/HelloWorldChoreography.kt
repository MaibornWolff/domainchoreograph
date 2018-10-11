package de.maibornwolff.domainchoreograph.examples.helloworld

import de.maibornwolff.domainchoreograph.core.api.DomainChoreography

@DomainChoreography
interface HelloWorldChoreography {
    fun calculate(name: Name): HelloWorld
}
