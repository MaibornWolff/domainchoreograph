package de.maibornwolff.domainchoreograph.analyticsserver.testingtool

import de.maibornwolff.domainchoreograph.analyticsserver.shared.MyDomainDefinition
import de.maibornwolff.domainchoreograph.analyticsserver.shared.NoDomainDefinition
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ScanForDomainDefinitions {
    @Test
    fun `should work`() {
        val definitions = scanForDomainDefinitions()

        assertThat(definitions).contains(MyDomainDefinition::class.java)
        assertThat(definitions).doesNotContain(NoDomainDefinition::class.java)
    }
}
