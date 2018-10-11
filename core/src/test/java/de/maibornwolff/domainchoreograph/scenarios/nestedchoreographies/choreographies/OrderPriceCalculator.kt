package de.maibornwolff.domainchoreograph.scenarios.nestedchoreographies.choreographies

import de.maibornwolff.domainchoreograph.core.api.DomainChoreography
import de.maibornwolff.domainchoreograph.scenarios.nestedchoreographies.domaintypes.ArticlePriceService
import de.maibornwolff.domainchoreograph.scenarios.nestedchoreographies.domaintypes.Order
import de.maibornwolff.domainchoreograph.scenarios.nestedchoreographies.domaintypes.OrderPrice

@DomainChoreography
interface OrderPriceCalculator {
    fun calculate(order: Order, articlePriceService: ArticlePriceService): OrderPrice
}
