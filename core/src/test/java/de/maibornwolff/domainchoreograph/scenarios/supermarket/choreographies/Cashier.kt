package de.maibornwolff.domainchoreograph.scenarios.supermarket.choreographies

import de.maibornwolff.domainchoreograph.core.api.DomainChoreography
import de.maibornwolff.domainchoreograph.scenarios.supermarket.domaindefinitions.Basket
import de.maibornwolff.domainchoreograph.scenarios.supermarket.domaindefinitions.BasketPrice
import de.maibornwolff.domainchoreograph.scenarios.supermarket.domaindefinitions.PriceService

@DomainChoreography
interface Cashier {
    fun checkout(basket: Basket, priceService: PriceService): BasketPrice
}
