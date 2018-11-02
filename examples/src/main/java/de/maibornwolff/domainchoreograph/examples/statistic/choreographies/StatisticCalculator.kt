package de.maibornwolff.domainchoreograph.examples.statistic.choreographies

import de.maibornwolff.domainchoreograph.core.api.DomainChoreography
import de.maibornwolff.domainchoreograph.examples.statistic.domaintypes.Statistic
import de.maibornwolff.domainchoreograph.examples.statistic.domaintypes.Sample

@DomainChoreography
interface StatisticCalculator {
    fun calculate(sample: Sample): Statistic
}
