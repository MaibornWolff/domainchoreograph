package de.maibornwolff.domainchoreograph.examples.statistik.domaintypes;


import de.maibornwolff.domainchoreograph.core.api.DomainDefinition;
import de.maibornwolff.domainchoreograph.core.api.DomainFunction;

@DomainDefinition
public class Anzahl {

    private int anzahl;

    public Anzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    @DomainFunction
    public static Anzahl resolve(Stichprobe stichprobe) {
        return new Anzahl(stichprobe.getStichprobe().size());
    }

    public int getAnzahl() {
        return anzahl;
    }

    @Override
    public String toString() {
        return "" + anzahl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Anzahl anzahl1 = (Anzahl) o;

        return anzahl == anzahl1.anzahl;
    }

    @Override
    public int hashCode() {
        return anzahl;
    }
}
