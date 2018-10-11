package de.maibornwolff.domainchoreograph.example.domaindefinitions.statistik

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction

@DomainDefinition
data class StdDev(val stdDev: Double) {

    override fun toString(): String {
        return "" + stdDev
    }

    companion object {

        @DomainFunction
        fun resolve(varianz: Varianz): StdDev {

            return StdDev(Math.sqrt(varianz.varianz))
        }
    }
}
