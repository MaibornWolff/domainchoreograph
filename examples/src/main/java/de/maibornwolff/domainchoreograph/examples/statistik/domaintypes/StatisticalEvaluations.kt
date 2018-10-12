package de.maibornwolff.domainchoreograph.examples.statistik.domaintypes

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction
import de.maibornwolff.domainchoreograph.examples.statistik.choreographies.StatisticCalculator

@DomainDefinition
data class StatisticalEvaluations(val statistics: List<Statistic>) {

    override fun toString(): String {
        return statistics.size.toString()
    }

    companion object {

        @DomainFunction
        fun resolve(statisticCalculator: StatisticCalculator, samplePool: SamplePool): StatisticalEvaluations {
            val statistics = samplePool.samples
                    .map{ statisticCalculator.calculate(it) }

            return StatisticalEvaluations(statistics)
        }
    }
}
