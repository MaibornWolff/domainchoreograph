package de.maibornwolff.domainchoreograph.examples.pricing.domaintypes

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction

@DomainDefinition
class PriceAfterTax(amount: Double) : Price(amount) {
    companion object {
        @DomainFunction
        fun calculate(priceAfterDiscount: PriceAfterDiscount, tax: Tax): PriceAfterTax {
            return PriceAfterTax(amount = priceAfterDiscount.amount * (1 + tax.percent))
        }
    }
}
