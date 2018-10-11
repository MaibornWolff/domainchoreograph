package de.maibornwolff.domainchoreograph.scenarios.nestedchoreographies.domaintypes

class ArticlePriceServiceImpl(vararg prices: Pair<String, Float>): ArticlePriceService {
    private val prices = mapOf(*prices)

    override fun getPrice(articleName: String) = prices[articleName]
}
