package de.maibornwolff.domainchoreograph.examples.pricing.domaintypes

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction
import de.maibornwolff.domainchoreograph.examples.pricing.services.DiscountService

@DomainDefinition
class Discount(val percent: Double) {
    override fun toString() = "${percent * 100}%"

    companion object {
        @DomainFunction
        fun get(discountService: DiscountService, product: Product, customer: Customer): Discount {
            return Discount(discountService.getDiscount(customer, product))
        }
    }
}
