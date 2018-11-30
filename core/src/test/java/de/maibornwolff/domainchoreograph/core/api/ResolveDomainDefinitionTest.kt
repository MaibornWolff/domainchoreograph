package de.maibornwolff.domainchoreograph.core.api

import de.maibornwolff.domainchoreograph.scenarios.deepchoreography.DeepDomainObject1
import de.maibornwolff.domainchoreograph.scenarios.deepchoreography.DeepDomainObject2
import de.maibornwolff.domainchoreograph.scenarios.nestedchoreography.NestedDomainResult
import de.maibornwolff.domainchoreograph.scenarios.simplechoreography.SimpleDomainObject1
import de.maibornwolff.domainchoreograph.scenarios.simplechoreography.SimpleDomainObject2
import de.maibornwolff.domainchoreograph.scenarios.simplechoreography.SimpleDomainObject3
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ResolveDomainDefinitionTest {
    @Test
    fun `resolve a simple domain definition during runtime`() {
        val result: SimpleDomainObject3 = resolveDomainDefinition(
            SimpleDomainObject1(1),
            SimpleDomainObject2(2)
        )

        assertThat(result.sum).isEqualTo(3)
    }

    @Test
    fun `resolve a nested domain definition during runtime`() {
        val result: NestedDomainResult = resolveDomainDefinition(
            DeepDomainObject1(1),
            DeepDomainObject2(2)
        )

        assertThat(result.value).isEqualTo(3)
    }
}
