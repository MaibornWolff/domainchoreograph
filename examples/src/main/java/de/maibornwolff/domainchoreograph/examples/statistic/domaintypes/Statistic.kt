package de.maibornwolff.domainchoreograph.examples.statistic.domaintypes

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction

@DomainDefinition
data class Statistic(
    val sample: Sample,
    val sampleSize: SampleSize,
    val sum: Sum,
    val meanValue: MeanValue,
    val variance: Variance,
    val stdDev: StdDev,
    val minimum: Minimum
) {

    companion object {

        @DomainFunction
        fun resolve(
            sample: Sample,
            sampleSize: SampleSize,
            sum: Sum,
            meanValue: MeanValue,
            variance: Variance,
            stdDev: StdDev,
            minimum: Minimum
        ): Statistic {

            return Statistic(
                sample,
                sampleSize,
                sum,
                meanValue,
                variance,
                stdDev,
                minimum
            )
        }
    }
}
