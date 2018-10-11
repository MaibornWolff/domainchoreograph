package de.maibornwolff.domainchoreograph.example.domaindefinitions

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.example.domaindefinitions.statistik.Stichprobe

@DomainDefinition
interface StichprobenService {

    val stichproben: List<Stichprobe>
}
