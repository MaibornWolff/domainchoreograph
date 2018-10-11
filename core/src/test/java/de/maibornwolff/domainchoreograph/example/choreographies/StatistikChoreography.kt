package de.maibornwolff.domainchoreograph.example.choreographies

import de.maibornwolff.domainchoreograph.core.api.DomainChoreography
import de.maibornwolff.domainchoreograph.example.domaindefinitions.statistik.Statistik
import de.maibornwolff.domainchoreograph.example.domaindefinitions.statistik.Stichprobe

@DomainChoreography
interface StatistikChoreography {

    fun calculate(stichprobe: Stichprobe): Statistik
}
