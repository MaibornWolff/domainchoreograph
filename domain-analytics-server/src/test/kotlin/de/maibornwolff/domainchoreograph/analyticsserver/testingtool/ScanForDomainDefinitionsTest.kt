package de.maibornwolff.domainchoreograph.analyticsserver.testingtool

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

@DomainDefinition
class MyDomainDefinition

class NoDomainDefinition

class ScanForDomainDefinitions {
    @Test
    fun `should work`() {
        val definitions = scanForDomainDefinitions()

        assertThat(definitions).contains(MyDomainDefinition::class.java)
        assertThat(definitions).doesNotContain(NoDomainDefinition::class.java)
    }
}
