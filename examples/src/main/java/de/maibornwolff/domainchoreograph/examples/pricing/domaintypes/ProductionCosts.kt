package de.maibornwolff.domainchoreograph.examples.pricing.domaintypes

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction

@DomainDefinition
class ProductionCosts(val amount: Double) {
    companion object {
        @DomainFunction
        fun get(product: Product) = ProductionCosts(product.productionCosts)
    }

    override fun toString() = "$amount\$"
}
