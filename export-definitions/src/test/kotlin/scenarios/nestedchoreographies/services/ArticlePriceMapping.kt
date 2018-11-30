package de.maibornwolff.domainchoreograph.scenarios.orderprice.services

import de.maibornwolff.domainchoreograph.scenarios.orderprice.domaintypes.ArticlePriceService

data class ArticlePriceMapping(val prices: Map<String, Float>): ArticlePriceService {

    override fun getPrice(articleName: String): Float? {
        return prices[articleName]
    }
}
