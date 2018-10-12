package de.maibornwolff.domainchoreograph.examples.statistik.domaintypes

import de.maibornwolff.domainchoreograph.core.api.DomainService

@DomainService
interface SampleAdapter {
    fun getSamples(): List<Sample>
}
