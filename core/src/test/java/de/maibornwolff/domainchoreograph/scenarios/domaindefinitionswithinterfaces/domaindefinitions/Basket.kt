package de.maibornwolff.domainchoreograph.scenarios.domaindefinitionswithinterfaces.domaindefinitions

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition

@DomainDefinition
class Basket(
    val articles: List<Article>
)
