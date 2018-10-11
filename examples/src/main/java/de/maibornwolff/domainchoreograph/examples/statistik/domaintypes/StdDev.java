package de.maibornwolff.domainchoreograph.examples.statistik.domaintypes;

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition;
import de.maibornwolff.domainchoreograph.core.api.DomainFunction;

@DomainDefinition
public class StdDev {

    private double stdDev;

    public StdDev(double stdDev) {
        this.stdDev = stdDev;
    }

    @DomainFunction
    public static StdDev resolve(Varianz varianz) {

        return new StdDev(Math.sqrt(varianz.getVarianz()));
    }

    @Override
    public String toString() {
        return "" + stdDev;
    }

    public double getStdDev() {
        return stdDev;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StdDev stdDev1 = (StdDev) o;

        return Double.compare(stdDev1.stdDev, stdDev) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(stdDev);
        return (int) (temp ^ (temp >>> 32));
    }
}
