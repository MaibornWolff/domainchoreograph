package de.maibornwolff.domainchoreograph.scenarios.nestedchoreographies.services

import de.maibornwolff.domainchoreograph.scenarios.nestedchoreographies.domaintypes.ArticlePriceService

data class ArticlePriceMapping(val prices: Map<String, Float>): ArticlePriceService {

    override fun getPrice(articleName: String): Float? {
        return prices[articleName]
    }
}
