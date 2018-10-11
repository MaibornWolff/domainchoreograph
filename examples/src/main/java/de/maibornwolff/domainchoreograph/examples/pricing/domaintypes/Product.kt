package de.maibornwolff.domainchoreograph.examples.pricing.domaintypes

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition

@DomainDefinition
class Product(
    val id: String,
    val productionCosts: Double
) {
    override fun toString() = id
}
