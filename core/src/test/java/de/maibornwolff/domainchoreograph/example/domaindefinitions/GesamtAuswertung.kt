package de.maibornwolff.domainchoreograph.example.domaindefinitions

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction
import de.maibornwolff.domainchoreograph.example.choreographies.StatistikChoreography
import de.maibornwolff.domainchoreograph.example.domaindefinitions.statistik.Statistik
import de.maibornwolff.domainchoreograph.example.domaindefinitions.statistik.Stichprobe

@DomainDefinition
data class GesamtAuswertung(val statistikList: List<Statistik>) {

    override fun toString(): String {
        return "Anzahl Gesamtauswertungen: " + statistikList.size
    }

    companion object {

        @DomainFunction
        fun resolve(statistikChoreography: StatistikChoreography, datenpool: Stichproben): GesamtAuswertung {

            val calculateStatistik = calculateStatistikFunction(statistikChoreography)

            val statistikList = datenpool.stichprobenList
                .map { stichprobe -> calculateStatistik(stichprobe) }
                .filterNotNull()

            return GesamtAuswertung(statistikList)
        }

        private fun calculateStatistikFunction(statistikChoreography: StatistikChoreography): (Stichprobe) -> Statistik? {
            return { stichprobe: Stichprobe ->
                try {
                    statistikChoreography.calculate(stichprobe)
                } catch (exception: IllegalArgumentException) {
                    null
                }
            }
        }
    }
}
