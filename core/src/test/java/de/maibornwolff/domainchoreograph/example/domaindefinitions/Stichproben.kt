package de.maibornwolff.domainchoreograph.example.domaindefinitions

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction
import de.maibornwolff.domainchoreograph.example.domaindefinitions.statistik.Stichprobe

@DomainDefinition
data class Stichproben(val stichprobenList: List<Stichprobe>) {

    override fun toString(): String {
        return "Anzahl Stichproben: " + stichprobenList.size
    }

    companion object {

        @DomainFunction
        fun resolveStichproben(stichprobenService: StichprobenService): Stichproben {
            val stichprobenList = stichprobenService.stichproben
            return Stichproben(stichprobenList)
        }
    }
}
