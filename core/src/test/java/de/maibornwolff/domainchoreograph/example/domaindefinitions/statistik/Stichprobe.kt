package de.maibornwolff.domainchoreograph.example.domaindefinitions.statistik

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition

@DomainDefinition
data class Stichprobe(val stichprobe: List<Int>)
