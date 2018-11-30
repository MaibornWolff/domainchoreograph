package de.maibornwolff.domainchoreograph.scenarios.orderprice.choreographies

import de.maibornwolff.domainchoreograph.core.api.DomainChoreography
import de.maibornwolff.domainchoreograph.scenarios.orderprice.domaintypes.Order
import de.maibornwolff.domainchoreograph.scenarios.orderprice.domaintypes.OrderPrice
import de.maibornwolff.domainchoreograph.scenarios.orderprice.services.ArticlePriceServiceImpl

@DomainChoreography
interface OrderPriceCalculatorWithImplementation {
    fun calculate(order: Order, articlePriceService: ArticlePriceServiceImpl): OrderPrice
}
