package de.maibornwolff.domainchoreograph.scenarios.nestedchoreographies.services

import de.maibornwolff.domainchoreograph.scenarios.nestedchoreographies.domaintypes.ArticlePriceService

class ArticlePriceServiceImpl(vararg prices: Pair<String, Float>): ArticlePriceService {
    private val prices = mapOf(*prices)

    override fun getPrice(articleName: String) = prices[articleName]
}
