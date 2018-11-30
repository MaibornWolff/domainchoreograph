package de.maibornwolff.domainchoreograph.scenarios.supermarket.domaindefinitions

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition

@DomainDefinition
interface PriceService{
    fun getPrice(item: Article): Double
}
