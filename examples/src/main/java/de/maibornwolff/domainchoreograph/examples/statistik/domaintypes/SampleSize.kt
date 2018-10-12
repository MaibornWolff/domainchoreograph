package de.maibornwolff.domainchoreograph.examples.statistik.domaintypes


import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction

@DomainDefinition
data class SampleSize(val value: Int) {

    override fun toString(): String {
        return value.toString()
    }

    companion object {

        @DomainFunction
        fun resolve(sample: Sample): SampleSize {
            return SampleSize(sample.size)
        }
    }
}
