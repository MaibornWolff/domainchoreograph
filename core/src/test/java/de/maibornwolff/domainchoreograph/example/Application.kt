package de.maibornwolff.domainchoreograph.example

import de.maibornwolff.domainchoreograph.core.api.DomainEnvironment
import de.maibornwolff.domainchoreograph.example.choreographies.GesamtAuswertungChoreography

object Application {

    @JvmStatic
    fun main(args: Array<String>) {

        val gesamtAuswertungChoreography = DomainEnvironment().get(
            GesamtAuswertungChoreography::class.java
        )

        val (statistikList) = gesamtAuswertungChoreography.calculate(StichprobenQuelle())

        for (statistik in statistikList) {
            println(statistik)
            println("----")
        }
    }
}
