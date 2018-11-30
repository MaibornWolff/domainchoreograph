package de.maibornwolff.domainchoreograph.scenarios.supermarket.domaindefinitions

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition

@DomainDefinition
class Basket(
    val articles: List<Article>
)
