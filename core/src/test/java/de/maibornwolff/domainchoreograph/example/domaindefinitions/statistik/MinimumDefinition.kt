package de.maibornwolff.domainchoreograph.example.domaindefinitions.statistik

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction

@DomainDefinition("Minimum")
object MinimumDefinition {

    @DomainFunction
    fun minimum(stichprobe: Stichprobe): Int {
        return stichprobe.stichprobe.stream()
                .mapToInt { a -> a }
                .min()
                .orElseThrow { IllegalArgumentException("Stichprobe darf nicht leer sein") }
    }
}
