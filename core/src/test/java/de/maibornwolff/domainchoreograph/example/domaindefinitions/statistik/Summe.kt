package de.maibornwolff.domainchoreograph.example.domaindefinitions.statistik

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction

@DomainDefinition
data class Summe(val summe: Int) {

    override fun toString(): String {
        return "" + summe
    }

    companion object {

        @DomainFunction
        fun resolve(stichprobe: Stichprobe): Summe {

            return Summe(stichprobe.stichprobe.stream()
                .reduce(0) { a, b -> a!! + b!! }
            )
        }
    }
}
