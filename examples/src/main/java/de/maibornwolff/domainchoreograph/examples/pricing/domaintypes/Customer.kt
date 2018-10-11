package de.maibornwolff.domainchoreograph.examples.pricing.domaintypes

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition

@DomainDefinition
class Customer(
    val id: String,
    val firstName: String,
    val lastName: String
) {
    override fun toString() = "$firstName $lastName"
}
