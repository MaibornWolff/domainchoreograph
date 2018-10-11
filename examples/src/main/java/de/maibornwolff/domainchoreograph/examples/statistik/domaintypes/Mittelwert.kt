package de.maibornwolff.domainchoreograph.examples.statistik.domaintypes

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction

@DomainDefinition
data class Mittelwert(val mittelwert: Double) {

    override fun toString(): String {
        return mittelwert.toString()
    }

    companion object {

        @DomainFunction
        fun resolve(anzahl: Anzahl, summe: Summe): Mittelwert {

            if (anzahl.anzahl == 0) {
                throw IllegalArgumentException("Die Größe der Stichprobe darf nicht 0 sein.")
            }

            return Mittelwert(summe.summe.toDouble() / anzahl.anzahl)
        }
    }
}
