package de.maibornwolff.domainchoreograph.scenarios.orderprice.domaintypes

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction
import de.maibornwolff.domainchoreograph.scenarios.orderprice.choreographies.ArticlePriceCalculator

@DomainDefinition
data class OrderPrice(val value: Float) {
    companion object {
        @DomainFunction
        fun calculate(order: Order, articlePriceService: ArticlePriceService, articlePriceCalculator: ArticlePriceCalculator): OrderPrice {
            return OrderPrice(
                order.articles.fold(0f) { sum, article ->
                    sum + articlePriceCalculator.calculate(article, articlePriceService).value
                }
            )
        }
    }
}
