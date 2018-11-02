package de.maibornwolff.domainchoreograph.examples.statistic.domaintypes

import de.maibornwolff.domainchoreograph.core.api.DomainService

@DomainService
interface SampleAdapter {
    fun getSamples(): List<Sample>
}
