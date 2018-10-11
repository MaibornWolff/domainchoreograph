package de.maibornwolff.domainchoreograph.examples.statistik.domaintypes;

import de.maibornwolff.domainchoreograph.core.api.DomainFunction;
import de.maibornwolff.domainchoreograph.core.api.DomainDefinition;

@DomainDefinition("Minimum")
public class MinimumFactory {

    @DomainFunction
    public static Integer minimum(Stichprobe stichprobe) {
        return stichprobe.getStichprobe().stream()
                .mapToInt(a -> a)
                .min()
                .orElseThrow(() -> new IllegalArgumentException("Stichprobe darf nicht leer sein"));
    }

}
