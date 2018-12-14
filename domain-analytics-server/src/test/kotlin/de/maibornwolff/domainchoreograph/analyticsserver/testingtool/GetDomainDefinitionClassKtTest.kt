package de.maibornwolff.domainchoreograph.analyticsserver.testingtool

import de.maibornwolff.domainchoreograph.analyticsserver.shared.MyDomainDefinition
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GetDomainDefinitionClassKtTest {
    @Test
    fun `should return domain definition class`() {
        val clazz = getDomainDefinitionClass(MyDomainDefinition::class.java.canonicalName)

        assertThat(clazz).isEqualTo(MyDomainDefinition::class.java)
    }

    @Test
    fun `should return null on other classed`() {
        val clazz = getDomainDefinitionClass(String::class.java.canonicalName)

        assertThat(clazz).isEqualTo(null)
    }
}
