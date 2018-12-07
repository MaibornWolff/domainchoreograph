package de.maibornwolff.domainchoreograph.analyticsserver.testingtool

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

@DomainDefinition
data class LeftNumber(val n: Int)

@DomainDefinition
data class RightNumber(val n: Int)

@DomainDefinition
data class Sum(val n: Int) {
    companion object {
        @DomainFunction
        fun calculate(a: RightNumber, b: LeftNumber) = Sum(a.n + b.n)
    }
}

class RunChoreographyFromJsonKtTest {
    @Test
    fun `should work`() {
        val jsonMapper = jacksonObjectMapper()

        assertThat(runChoreographyFromJson(
            Sum::class.java.canonicalName,
            setOf(
                JsonEncodedClass(
                    RightNumber::class.java.canonicalName,
                    jsonMapper.writeValueAsString(RightNumber(1))
                ),
                JsonEncodedClass(
                    LeftNumber::class.java.canonicalName,
                    jsonMapper.writeValueAsString(LeftNumber(2))
                )
            )
        )).isEqualTo(Sum(3))
    }
}
