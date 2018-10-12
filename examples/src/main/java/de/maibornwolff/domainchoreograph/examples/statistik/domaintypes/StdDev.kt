package de.maibornwolff.domainchoreograph.examples.statistik.domaintypes

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction

@DomainDefinition
data class StdDev(val value: Double) {

    override fun toString(): String {
        return value.toString()
    }

    companion object {

        @DomainFunction
        fun resolve(variance: Variance): StdDev {
            return StdDev(Math.sqrt(variance.value))
        }
    }
}
