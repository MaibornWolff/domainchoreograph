package de.maibornwolff.domainchoreograph.examples.statistic.domaintypes

import com.fasterxml.jackson.annotation.JsonIgnore
import de.maibornwolff.domainchoreograph.core.api.DomainDefinition

@DomainDefinition
data class Sample(val value: List<Int>) {

    val size: Int
        @JsonIgnore
        get() = value.size

    override fun toString(): String {
        return value.toString()
    }
}
