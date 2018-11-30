package de.maibornwolff.domainchoreograph.scenarios.orderprice.services

import de.maibornwolff.domainchoreograph.scenarios.orderprice.domaintypes.ArticlePriceService

class ArticlePriceServiceImpl(vararg prices: Pair<String, Float>): ArticlePriceService {
    private val prices = mapOf(*prices)

    override fun getPrice(articleName: String) = prices[articleName]
}
