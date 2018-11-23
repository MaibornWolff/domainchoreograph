package de.maibornwolff.domainchoreograph.core.api

import com.nhaarman.mockitokotlin2.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

@DomainChoreography
interface TestChoreography

internal class DomainEnvironmentTest {
    @Test
    fun `should get DomainChoreography with Kotlin API`() {
        val environment = DomainEnvironment()

        val choreography = environment.get<TestChoreography>()

        assertThat(choreography).isInstanceOf(TestChoreography::class.java)
    }

    @Test
    fun `should get DomainChoreography with Java API`() {
        val environment = DomainEnvironment()

        val choreography = environment.get(TestChoreography::class.java)

        assertThat(choreography).isInstanceOf(TestChoreography::class.java)
    }

    @Test
    fun `should get DomainChoreographyMeta`() {
        val environment = DomainEnvironment()

        val choreography = environment.getMeta(TestChoreography::class.java)

        assertThat(choreography).isInstanceOf(DomainChoreographyMeta::class.java)
    }
}

internal class DomainEnvironmentBuilderTest {
    @Test
    fun `should build environment`() {
        val mockLogger1 = mock<DomainLogger>()
        val mockLogger2 = mock<DomainLogger>()
        val environment = DomainEnvironment.builder()
            .addLogger(mockLogger1)
            .addLogger(mockLogger2)
            .build()

        assertThat(environment.options!!.logger).containsOnly(mockLogger1, mockLogger2)
    }
}
