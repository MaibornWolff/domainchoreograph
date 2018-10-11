package de.maibornwolff.domainchoreograph.example.choreographies

import de.maibornwolff.domainchoreograph.core.api.DomainChoreography
import de.maibornwolff.domainchoreograph.example.domaindefinitions.GesamtAuswertung
import de.maibornwolff.domainchoreograph.example.domaindefinitions.StichprobenService

@DomainChoreography
interface GesamtAuswertungChoreography {

    fun calculate(stichprobenService: StichprobenService): GesamtAuswertung
}
