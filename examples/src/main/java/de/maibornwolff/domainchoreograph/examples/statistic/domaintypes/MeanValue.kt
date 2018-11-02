package de.maibornwolff.domainchoreograph.examples.statistic.domaintypes

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction

@DomainDefinition
data class MeanValue(val value: Double) {

    override fun toString(): String {
        return value.toString()
    }

    companion object {

        @DomainFunction
        fun resolve(sampleSize: SampleSize, sum: Sum): MeanValue {

            if (sampleSize.value == 0) {
                throw IllegalArgumentException("The size of a sample can not be zero.")
            }

            return MeanValue(sum.value.toDouble() / sampleSize.value)
        }
    }
}
