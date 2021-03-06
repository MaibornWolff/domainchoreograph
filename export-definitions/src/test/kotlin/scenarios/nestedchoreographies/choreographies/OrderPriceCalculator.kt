package de.maibornwolff.domainchoreograph.scenarios.orderprice.choreographies

import de.maibornwolff.domainchoreograph.core.api.DomainChoreography
import de.maibornwolff.domainchoreograph.scenarios.orderprice.domaintypes.ArticlePriceServiceImpl
import de.maibornwolff.domainchoreograph.scenarios.orderprice.domaintypes.Order
import de.maibornwolff.domainchoreograph.scenarios.orderprice.domaintypes.OrderPrice

@DomainChoreography()
interface OrderPriceCalculator {
    fun calculate(order: Order, articlePriceService: ArticlePriceServiceImpl): OrderPrice
}
