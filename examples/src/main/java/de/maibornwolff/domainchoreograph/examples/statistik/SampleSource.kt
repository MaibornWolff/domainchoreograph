package de.maibornwolff.domainchoreograph.examples.statistik

import de.maibornwolff.domainchoreograph.examples.statistik.domaintypes.Sample
import de.maibornwolff.domainchoreograph.examples.statistik.domaintypes.SampleAdapter
import java.util.*

class SampleSource : SampleAdapter {

    override fun getSamples(): List<Sample> {
        return generateSamples()
    }

    companion object {

        fun generateSamples(): List<Sample> {
            val stichproben = ArrayList<Sample>()
            stichproben.add(Sample(Arrays.asList(1, 2, 3, 4, 5)))
            stichproben.add(Sample(Arrays.asList(5, 9, 4, 3, 1)))
            stichproben.add(Sample(Arrays.asList(4, 4, 5)))

            return stichproben
        }
    }
}
