package de.maibornwolff.domainchoreograph.scenarios.orderprice.domaintypes

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition

@DomainDefinition
data class Order(val articles: Set<Article>)
