package de.maibornwolff.domainchoreograph.examples.statistik.choreographies;

import de.maibornwolff.domainchoreograph.examples.statistik.domaintypes.Statistik;
import de.maibornwolff.domainchoreograph.examples.statistik.domaintypes.Stichprobe;
import de.maibornwolff.domainchoreograph.core.api.DomainChoreography;

@DomainChoreography
public interface StatistikCalculator {
    Statistik calculate(Stichprobe stichprobe);
}
