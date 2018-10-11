package de.maibornwolff.domainchoreograph.scenarios.nestedchoreographies.domaintypes

import de.maibornwolff.domainchoreograph.core.api.DomainService

@DomainService
interface ArticlePriceService {
    fun getPrice(articleName: String): Float?
}
