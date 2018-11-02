package de.maibornwolff.domainchoreograph.examples.statistic.choreographies

import de.maibornwolff.domainchoreograph.core.api.DomainChoreography
import de.maibornwolff.domainchoreograph.examples.statistic.SampleSource
import de.maibornwolff.domainchoreograph.examples.statistic.domaintypes.StatisticalEvaluations

@DomainChoreography
interface StatisticalEvaluationsCalculator {
    fun evaluate(sampleSource: SampleSource): StatisticalEvaluations
}
