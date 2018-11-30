package de.maibornwolff.domainchoreograph.scenarios.supermarket.domaindefinitions

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition

@DomainDefinition
data class Article (
    val name: String
)
