package de.maibornwolff.domainchoreograph.examples.pricing.domaintypes

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction

@DomainDefinition
class PriceAfterDiscount(amount: Double) : Price(amount) {
    companion object {
        @DomainFunction
        fun calculate(productionCosts: ProductionCosts, discount: Discount): PriceAfterDiscount {
            return PriceAfterDiscount(amount = productionCosts.amount * (1 - discount.percent))
        }
    }
}
