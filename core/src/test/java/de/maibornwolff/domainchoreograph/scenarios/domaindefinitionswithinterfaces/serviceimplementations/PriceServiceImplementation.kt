package de.maibornwolff.domainchoreograph.scenarios.domaindefinitionswithinterfaces.serviceimplementations

import de.maibornwolff.domainchoreograph.scenarios.domaindefinitionswithinterfaces.domaindefinitions.Article
import de.maibornwolff.domainchoreograph.scenarios.domaindefinitionswithinterfaces.domaindefinitions.PriceService

class PriceServiceImplementation : PriceService {
    private val prices = mapOf(
        "banana" to .5,
        "apple" to 1.0
    )

    override fun getPrice(item: Article): Double {
        return prices[item.name]!!
    }
}
