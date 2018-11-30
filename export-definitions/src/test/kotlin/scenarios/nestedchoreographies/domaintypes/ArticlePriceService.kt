package de.maibornwolff.domainchoreograph.scenarios.orderprice.domaintypes

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition

@DomainDefinition
interface ArticlePriceService {
    fun getPrice(articleName: String): Float?
}
