package de.maibornwolff.domainchoreograph.examples.statistik.domaintypes

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction

@DomainDefinition
data class Minimum(val value: Int) {

    override fun toString(): String {
        return value.toString()
    }

    companion object {

        @DomainFunction
        fun resolve(sample: Sample): Minimum {
            val min = sample.value.min() ?: throw IllegalArgumentException("Sample darf nicht leer sein")
            return Minimum(min)
        }
    }
}
