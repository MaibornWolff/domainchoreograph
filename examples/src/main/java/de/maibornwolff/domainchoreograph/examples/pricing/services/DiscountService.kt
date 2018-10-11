package de.maibornwolff.domainchoreograph.examples.pricing.services

import de.maibornwolff.domainchoreograph.core.api.DomainService
import de.maibornwolff.domainchoreograph.examples.pricing.domaintypes.Customer
import de.maibornwolff.domainchoreograph.examples.pricing.domaintypes.Discount
import de.maibornwolff.domainchoreograph.examples.pricing.domaintypes.Product
import java.lang.RuntimeException

open class DiscountNotFonndException(message: String) : RuntimeException(message)
class ProductDiscountNotFoundException(product: Product) : DiscountNotFonndException("No discount found for product $product")
class CustomerDiscountNotFoundException(customer: Customer) : DiscountNotFonndException("No discount found for customer $customer")

@DomainService
class DiscountService {
    private val customerDiscounts = mapOf(
        "0" to .05,
        "1" to .00,
        "2" to .03
    )
        private val productDiscounts = mapOf(
        "phone" to .03,
        "tv" to .1,
        "laptop" to .08
    )

    fun getDiscount(customer: Customer, product: Product): Double {
        val customerDiscount = customerDiscounts[customer.id] ?: throw CustomerDiscountNotFoundException(customer)
        val productDiscount = productDiscounts[product.id] ?: throw ProductDiscountNotFoundException(product)
        return customerDiscount + productDiscount
    }

    override fun toString(): String = "DiscountService"
}

