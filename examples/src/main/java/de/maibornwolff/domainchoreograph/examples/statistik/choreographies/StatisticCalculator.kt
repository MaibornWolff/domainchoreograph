package de.maibornwolff.domainchoreograph.examples.statistik.choreographies

import de.maibornwolff.domainchoreograph.core.api.DomainChoreography
import de.maibornwolff.domainchoreograph.examples.statistik.domaintypes.Statistic
import de.maibornwolff.domainchoreograph.examples.statistik.domaintypes.Sample

@DomainChoreography
interface StatisticCalculator {
    fun calculate(sample: Sample): Statistic
}
