package de.maibornwolff.domainchoreograph.examples.statistik.domaintypes;

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition;
import de.maibornwolff.domainchoreograph.core.api.DomainFunction;

@DomainDefinition
public class Summe {

    private int summe;

    public Summe(int summe) {
        this.summe = summe;
    }

    @DomainFunction
    public static Summe resolve(Stichprobe stichprobe) {
        return new Summe(stichprobe.getStichprobe().stream()
                .reduce(0, (a, b) -> a + b)
        );
    }

    @Override
    public String toString() {
        return "" + summe;
    }

    public int getSumme() {
        return summe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Summe summe1 = (Summe) o;

        return summe == summe1.summe;
    }

    @Override
    public int hashCode() {
        return summe;
    }
}
