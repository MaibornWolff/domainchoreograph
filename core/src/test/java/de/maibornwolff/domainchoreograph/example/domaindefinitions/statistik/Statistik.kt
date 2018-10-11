package de.maibornwolff.domainchoreograph.example.domaindefinitions.statistik

import de.maibornwolff.domainchoreograph.core.api.DefinedBy
import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction

@DomainDefinition
data class Statistik(
    val stichprobe: Stichprobe,
    val anzahl: Anzahl,
    val summe: Summe,
    val mittelwert: Mittelwert,
    val varianz: Varianz,
    val stdDev: StdDev,
    val minimum: Int?
) {

    override fun toString(): String {
        return "Statistik{" +
            "stichprobe=" + stichprobe +
            ", anzahl=" + anzahl +
            ", summe=" + summe +
            ", mittelwert=" + mittelwert +
            ", varianz=" + varianz +
            ", stdDev=" + stdDev +
            ", min=" + minimum +
            '}'.toString()
    }

    companion object {

        @DomainFunction
        fun resolve(
            stichprobe: Stichprobe,
            anzahl: Anzahl,
            summe: Summe,
            mittelwert: Mittelwert,
            varianz: Varianz,
            stdDev: StdDev,
            @de.maibornwolff.domainchoreograph.core.api.DefinedBy(MinimumDefinition::class) minimum: Int?
        ): Statistik {

            return Statistik(
                stichprobe,
                anzahl,
                summe,
                mittelwert,
                varianz,
                stdDev,
                minimum
            )
        }
    }
}
