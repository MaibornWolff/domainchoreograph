package de.maibornwolff.domainchoreograph.scenarios.supermarket.domaindefinitions

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction

@DomainDefinition
data class BasketPrice(val amount: Double) {

    companion object {

        @DomainFunction
        fun get(basket: Basket, priceService: PriceService): BasketPrice {
            val amount = basket.articles.sumByDouble {
                priceService.getPrice(it)
            }
            return BasketPrice(amount)
        }
    }
}
