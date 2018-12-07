package de.maibornwolff.domainchoreograph.scenarios.nestedchoreography

import de.maibornwolff.domainchoreograph.core.api.DomainChoreography
import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction
import de.maibornwolff.domainchoreograph.scenarios.deepchoreography.DeepChoreography
import de.maibornwolff.domainchoreograph.scenarios.deepchoreography.DeepDomainObject1
import de.maibornwolff.domainchoreograph.scenarios.deepchoreography.DeepDomainObject2

@DomainDefinition
data class NestedDomainResult(val value: Int) {
    companion object {
        @DomainFunction
        fun create(d1: DeepDomainObject1, d2: DeepDomainObject2, deepChoreography: DeepChoreography) = NestedDomainResult(
            value = d1.n * deepChoreography.calculate(d1, d2).sum
        )
    }
}

@DomainChoreography
interface NestedChoreography {
    fun calculate(d1: DeepDomainObject1, d2: DeepDomainObject2): NestedDomainResult
}
