package de.maibornwolff.domainchoreograph.examples.statistik.domaintypes

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction

@DomainDefinition
data class SamplePool(val samples: List<Sample>) {

    override fun toString(): String {
        return samples.size.toString()
    }

    companion object {

        @DomainFunction
        fun resolve(sampleAdapter: SampleAdapter): SamplePool {
            val samples = sampleAdapter.getSamples()
            return SamplePool(samples)
        }
    }
}
