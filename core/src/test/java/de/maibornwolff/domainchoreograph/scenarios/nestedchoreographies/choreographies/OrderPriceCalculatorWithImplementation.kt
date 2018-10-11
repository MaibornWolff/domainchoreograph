package de.maibornwolff.domainchoreograph.scenarios.nestedchoreographies.choreographies

import de.maibornwolff.domainchoreograph.core.api.DomainChoreography
import de.maibornwolff.domainchoreograph.scenarios.nestedchoreographies.domaintypes.Order
import de.maibornwolff.domainchoreograph.scenarios.nestedchoreographies.domaintypes.OrderPrice
import de.maibornwolff.domainchoreograph.scenarios.nestedchoreographies.services.ArticlePriceServiceImpl

@DomainChoreography
interface OrderPriceCalculatorWithImplementation {
    fun calculate(order: Order, articlePriceService: ArticlePriceServiceImpl): OrderPrice
}
