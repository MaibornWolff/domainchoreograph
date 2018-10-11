package de.maibornwolff.domainchoreograph.scenarios.domaindefinitionswithinterfaces.domaindefinitions

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition

@DomainDefinition
interface PriceService{
    fun getPrice(item: Article): Double
}
