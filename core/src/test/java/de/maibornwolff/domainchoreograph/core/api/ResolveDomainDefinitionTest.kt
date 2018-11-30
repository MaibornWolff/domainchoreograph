package de.maibornwolff.domainchoreograph.core.api

import de.maibornwolff.domainchoreograph.scenarios.simplechoreography.SimpleDomainObject1
import de.maibornwolff.domainchoreograph.scenarios.simplechoreography.SimpleDomainObject2
import de.maibornwolff.domainchoreograph.scenarios.simplechoreography.SimpleDomainObject3
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ResolveDomainDefinitionTest {
    @Test
    fun `resolve a domain definition during runtime`() {
        val result: SimpleDomainObject3 = resolveDomainDefinition(
            SimpleDomainObject1(1),
            SimpleDomainObject2(2)
        )

        assertThat(result.sum).isEqualTo(3)
    }
}
