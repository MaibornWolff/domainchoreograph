package de.maibornwolff.domainchoreograph.example.orchestration

import de.maibornwolff.domainchoreograph.example.domaindefinitions.GesamtAuswertung
import de.maibornwolff.domainchoreograph.example.domaindefinitions.StichprobenService
import de.maibornwolff.domainchoreograph.example.domaindefinitions.statistik.*
import kotlin.streams.toList

object StatistikService {

    fun calculateGesamtAuswertung(stichprobenService: StichprobenService): GesamtAuswertung {

        val stichproben = stichprobenService.stichproben

        val statistikList = stichproben.stream()
            .map { stichprobe ->

                val anzahl = Anzahl.resolve(stichprobe)
                val summe = Summe.resolve(stichprobe)
                val mittelwert = Mittelwert.resolve(anzahl, summe)
                val varianz = Varianz.resolve(mittelwert, stichprobe, anzahl)
                val stdDev = StdDev.resolve(varianz)
                val minimum = MinimumDefinition.minimum(stichprobe)

                Statistik.resolve(stichprobe, anzahl, summe, mittelwert, varianz, stdDev, minimum)
            }
            .toList()

        return GesamtAuswertung(statistikList)
    }
}
