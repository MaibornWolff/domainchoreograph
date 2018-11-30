package de.maibornwolff.domainchoreograph.scenarios.simplechoreography

import de.maibornwolff.domainchoreograph.core.api.DomainChoreography
import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction

@DomainDefinition
class SimpleDomainObject1(val n: Int)

@DomainDefinition
class SimpleDomainObject2(val n: Int)

@DomainDefinition
class SimpleDomainObject3(val sum: Int) {
    companion object {
        @DomainFunction
        fun create(d1: SimpleDomainObject1, d2: SimpleDomainObject2) = SimpleDomainObject3(d1.n + d2.n)
    }
}


@DomainChoreography
interface SimpleChoreography {
    fun calculate(d1: SimpleDomainObject1, d2: SimpleDomainObject2): SimpleDomainObject3
}
