package de.maibornwolff.domainchoreograph.examples.pricing.choreographies

import de.maibornwolff.domainchoreograph.core.api.DomainChoreography
import de.maibornwolff.domainchoreograph.examples.pricing.domaintypes.Customer
import de.maibornwolff.domainchoreograph.examples.pricing.domaintypes.PriceAfterTax
import de.maibornwolff.domainchoreograph.examples.pricing.domaintypes.Product
import de.maibornwolff.domainchoreograph.examples.pricing.domaintypes.Tax
import de.maibornwolff.domainchoreograph.examples.pricing.services.DiscountService

@DomainChoreography
interface ProductPriceChoreography {
    fun calculatePriceAfterTax(
        product: Product,
        customer: Customer,
        discountService: DiscountService,
        tax: Tax
    ): PriceAfterTax
}
