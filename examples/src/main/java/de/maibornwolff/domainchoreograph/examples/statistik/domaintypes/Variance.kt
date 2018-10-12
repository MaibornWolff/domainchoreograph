package de.maibornwolff.domainchoreograph.examples.statistik.domaintypes


import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction
import kotlin.math.pow

@DomainDefinition
class Variance(val value: Double) {

    override fun toString(): String {
        return value.toString()
    }

    companion object {

        @DomainFunction
        fun resolve(meanValue: MeanValue, sample: Sample, sampleSize: SampleSize): Variance {

            val varianceValue = sample.value
                .map { a -> meanValue.value - a }
                .map { a -> a.pow(2) }
                .sum() * (1.0 / sampleSize.value)

            return Variance(varianceValue)
        }
    }
}
