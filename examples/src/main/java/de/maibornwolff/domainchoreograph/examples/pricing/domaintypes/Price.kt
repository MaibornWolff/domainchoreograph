package de.maibornwolff.domainchoreograph.examples.pricing.domaintypes

open class Price(
    val amount: Double
) {
    override fun toString() = "$amount\$"
}

