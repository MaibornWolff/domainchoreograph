package de.maibornwolff.domainchoreograph.example

import de.maibornwolff.domainchoreograph.example.domaindefinitions.StichprobenService
import de.maibornwolff.domainchoreograph.example.domaindefinitions.statistik.Stichprobe
import java.util.*

class StichprobenQuelle : StichprobenService {

    override val stichproben: List<Stichprobe>
        get() = generateStichprobenPool()

    override fun toString(): String {
        return "(StichprobenQuelle)"
    }

    companion object {

        fun generateStichprobenPool(): List<Stichprobe> {
            val stichproben = ArrayList<Stichprobe>()
            stichproben.add(Stichprobe(Arrays.asList(1, 2, 3, 4, 5)))
            stichproben.add(Stichprobe(Arrays.asList(5, 9, 4, 3, 1)))
            stichproben.add(Stichprobe(Arrays.asList(2, 4, 6, 8)))

            return stichproben
        }
    }
}
