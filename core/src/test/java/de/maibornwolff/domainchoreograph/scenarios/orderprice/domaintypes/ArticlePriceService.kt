package de.maibornwolff.domainchoreograph.scenarios.orderprice.domaintypes

import de.maibornwolff.domainchoreograph.core.api.DomainService

@DomainService
interface ArticlePriceService {
    fun getPrice(articleName: String): Float?
}
