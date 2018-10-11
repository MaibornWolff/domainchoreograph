package de.maibornwolff.domainchoreograph.examples.pricing.domaintypes

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition

@DomainDefinition
class Tax(
    val percent: Double
) {
    override fun toString() = "${percent * 100}%"
}
