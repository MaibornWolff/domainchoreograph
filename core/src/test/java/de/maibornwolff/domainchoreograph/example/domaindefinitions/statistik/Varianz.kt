package de.maibornwolff.domainchoreograph.example.domaindefinitions.statistik


import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction

@DomainDefinition
data class Varianz(val varianz: Double) {

    override fun toString(): String {
        return "" + varianz
    }

    companion object {

        @DomainFunction
        fun resolve(mittelwert: Mittelwert, stichprobe: Stichprobe, anzahl: Anzahl): Varianz {
            val variance = stichprobe.stichprobe.stream()
                .map { a -> mittelwert.mittelwert - a!! }
                .mapToDouble { a -> Math.pow(a, 2.0) }
                .sum() * (1.0 / anzahl.anzahl)

            return Varianz(variance)
        }
    }
}
