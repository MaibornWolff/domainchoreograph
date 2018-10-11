package de.maibornwolff.domainchoreograph.examples.statistik.domaintypes;

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition;

import java.util.Arrays;
import java.util.List;

@DomainDefinition
public class Stichprobe {

    private List<Integer> stichprobe;

    public Stichprobe(List<Integer> input) {
        this.stichprobe = input;
    }

    public List<Integer> getStichprobe() {
        return stichprobe;
    }

    @Override
    public String toString() {
        return Arrays.toString(stichprobe.toArray());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stichprobe that = (Stichprobe) o;

        return stichprobe != null ? stichprobe.equals(that.stichprobe) : that.stichprobe == null;
    }

    @Override
    public int hashCode() {
        return stichprobe != null ? stichprobe.hashCode() : 0;
    }
}
