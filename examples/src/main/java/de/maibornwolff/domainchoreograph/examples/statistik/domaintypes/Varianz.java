package de.maibornwolff.domainchoreograph.examples.statistik.domaintypes;


import de.maibornwolff.domainchoreograph.core.api.DomainDefinition;
import de.maibornwolff.domainchoreograph.core.api.DomainFunction;

@DomainDefinition
public class Varianz {

    private double varianz;

    public Varianz(double varianz) {
        this.varianz = varianz;
    }

    public double getVarianz() {
        return varianz;
    }

    @DomainFunction
    public static Varianz resolve(Mittelwert mittelwert, Stichprobe stichprobe, Anzahl anzahl) {

        double variance = stichprobe.getStichprobe().stream()
                .map(a -> mittelwert.getMittelwert() - a)
                .mapToDouble(a -> Math.pow(a, 2.0d))
                .sum() * (1d/anzahl.getAnzahl());

        return new Varianz(variance);
    }

    @Override
    public String toString() {
        return "" + varianz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Varianz varianz1 = (Varianz) o;

        return Double.compare(varianz1.varianz, varianz) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(varianz);
        return (int) (temp ^ (temp >>> 32));
    }
}
