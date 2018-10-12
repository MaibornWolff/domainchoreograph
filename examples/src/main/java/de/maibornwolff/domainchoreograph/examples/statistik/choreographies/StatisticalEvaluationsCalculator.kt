package de.maibornwolff.domainchoreograph.examples.statistik.choreographies

import de.maibornwolff.domainchoreograph.core.api.DomainChoreography
import de.maibornwolff.domainchoreograph.examples.statistik.SampleSource
import de.maibornwolff.domainchoreograph.examples.statistik.domaintypes.StatisticalEvaluations

@DomainChoreography
interface StatisticalEvaluationsCalculator {
    fun evaluate(sampleSource: SampleSource): StatisticalEvaluations
}
