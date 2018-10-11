package de.maibornwolff.domainchoreograph.examples.pricing

import de.maibornwolff.domainchoreograph.core.api.DomainEnvironment
import de.maibornwolff.domainchoreograph.domainanalytics.DomainAnalytics
import de.maibornwolff.domainchoreograph.examples.pricing.choreographies.ProductPriceChoreography
import de.maibornwolff.domainchoreograph.examples.pricing.domaintypes.Customer
import de.maibornwolff.domainchoreograph.examples.pricing.domaintypes.Product
import de.maibornwolff.domainchoreograph.examples.pricing.domaintypes.Tax
import de.maibornwolff.domainchoreograph.examples.pricing.services.DiscountService

fun main(args: Array<String>) {
    val analytics = DomainAnalytics()
    analytics.server.start()

    val environment = DomainEnvironment(logger = setOf(analytics.logger))
    val productPriceChoreography = environment.get<ProductPriceChoreography>()


    val tax = Tax(.19)
    val walterWhite = Customer(
        id = "0",
        firstName = "Walter",
        lastName = "White"
    )
    val homerSimpson = Customer(
        id = "1",
        firstName = "Homer",
        lastName = "Simpson"
    )

    val phone = Product("phone", 500.00)
    val tv = Product("tv", 800.00)
    val laptop = Product("laptop", 1_800.00)

    val discountService = DiscountService()

    var price = productPriceChoreography.calculatePriceAfterTax(
        product = phone,
        customer = homerSimpson,
        discountService = discountService,
        tax = tax
    )
    println("The price is $price")
    waitForInput()

    price = productPriceChoreography.calculatePriceAfterTax(
        product = tv,
        customer = walterWhite,
        discountService = discountService,
        tax = tax
    )
    println("The price is $price")
    waitForInput()

    price = productPriceChoreography.calculatePriceAfterTax(
        product = laptop,
        customer = homerSimpson,
        discountService = discountService,
        tax = tax
    )
    println("The price is $price")
    waitForInput()
}

fun waitForInput() {
    readLine()
}
