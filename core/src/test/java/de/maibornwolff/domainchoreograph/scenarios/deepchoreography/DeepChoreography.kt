package de.maibornwolff.domainchoreograph.scenarios.deepchoreography

import de.maibornwolff.domainchoreograph.core.api.DomainChoreography
import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction

@DomainDefinition
data class DeepDomainObject1(val n: Int)

@DomainDefinition
data class DeepDomainObject2(val n: Int)

@DomainDefinition
data class DeepDomainObject3(val sum: Int) {
    companion object {
        @DomainFunction
        fun create(d1: DeepDomainObject1, d2: DeepDomainObject2) = DeepDomainObject3(d1.n + d2.n)
    }
}

@DomainDefinition
data class DeepDomainObject4(val product: Double) {
    companion object {
        @DomainFunction
        fun create(d3: DeepDomainObject3) = DeepDomainObject4(d3.sum * 2.0)
    }
}

@DomainDefinition
data class DeepDomainObject5(val sum: Int, val product: Double) {
    companion object {
        @DomainFunction
        fun create(d3: DeepDomainObject3, d4: DeepDomainObject4) = DeepDomainObject5(
            sum = d3.sum,
            product = d4.product
        )
    }
}

@DomainChoreography
interface DeepChoreography {
    fun calculate(d1: DeepDomainObject1, d2: DeepDomainObject2): DeepDomainObject5
}
