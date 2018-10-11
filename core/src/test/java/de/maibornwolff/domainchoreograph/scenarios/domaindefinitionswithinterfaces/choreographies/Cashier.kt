package de.maibornwolff.domainchoreograph.scenarios.domaindefinitionswithinterfaces.choreographies

import de.maibornwolff.domainchoreograph.core.api.DomainChoreography
import de.maibornwolff.domainchoreograph.scenarios.domaindefinitionswithinterfaces.domaindefinitions.Basket
import de.maibornwolff.domainchoreograph.scenarios.domaindefinitionswithinterfaces.domaindefinitions.BasketPrice
import de.maibornwolff.domainchoreograph.scenarios.domaindefinitionswithinterfaces.domaindefinitions.PriceService

@DomainChoreography
interface Cashier {
    fun checkout(basket: Basket, priceService: PriceService): BasketPrice
}
