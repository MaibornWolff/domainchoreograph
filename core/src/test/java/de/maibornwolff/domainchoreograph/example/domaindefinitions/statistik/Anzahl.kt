package de.maibornwolff.domainchoreograph.example.domaindefinitions.statistik

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction

@DomainDefinition
data class Anzahl(val anzahl: Int) {

    override fun toString(): String {
        return "" + anzahl
    }

    companion object {
        @DomainFunction
        fun resolve(stichprobe: Stichprobe): Anzahl {
            return Anzahl(stichprobe.stichprobe.size)
        }
    }
}
