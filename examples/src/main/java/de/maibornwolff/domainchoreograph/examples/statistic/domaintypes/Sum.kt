package de.maibornwolff.domainchoreograph.examples.statistic.domaintypes

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction

@DomainDefinition
data class Sum(val value: Int) {

    override fun toString(): String {
        return value.toString()
    }

    companion object {

        @DomainFunction
        fun resolve(sample: Sample): Sum {
            return Sum(sample.value.sum())
        }
    }
}
