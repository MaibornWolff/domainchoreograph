package de.maibornwolff.domainchoreograph.examples.statistik.domaintypes;

import de.maibornwolff.domainchoreograph.core.api.DefinedBy;
import de.maibornwolff.domainchoreograph.core.api.DomainDefinition;
import de.maibornwolff.domainchoreograph.core.api.DomainFunction;

@DomainDefinition
public class Statistik {

    private Stichprobe stichprobe;
    private Anzahl anzahl;
    private Summe summe;
    private Mittelwert mittelwert;
    private Varianz varianz;
    private StdDev stdDev;
    private Integer minimum;

    public Statistik(
            Stichprobe stichprobe,
            Anzahl anzahl,
            Summe summe,
            Mittelwert mittelwert,
            Varianz varianz,
            StdDev stdDev,
            Integer minimum) {

        this.stichprobe = stichprobe;
        this.anzahl = anzahl;
        this.summe = summe;
        this.mittelwert = mittelwert;
        this.stdDev = stdDev;
        this.varianz = varianz;
        this.minimum = minimum;
    }

    @DomainFunction
    public static Statistik resolve(
            Stichprobe stichprobe,
            Anzahl anzahl,
            Summe summe,
            Mittelwert mittelwert,
            Varianz varianz,
            StdDev stdDev,
            @DefinedBy(MinimumFactory.class) Integer minimum) {

        return new Statistik(
                stichprobe,
                anzahl,
                summe,
                mittelwert,
                varianz,
                stdDev,
                minimum
        );
    }

    public Stichprobe getStichprobe() {
        return stichprobe;
    }

    public Anzahl getAnzahl() {
        return anzahl;
    }

    public Summe getSumme() {
        return summe;
    }

    public Mittelwert getMittelwert() {
        return mittelwert;
    }

    public StdDev getStdDev() {
        return stdDev;
    }

    public Varianz getVarianz() {
        return varianz;
    }

    public Integer getMinimum() {
        return minimum;
    }

    @Override
    public String toString() {
        return "Statistik{" +
                "stichprobe=" + stichprobe +
                ", anzahl=" + anzahl +
                ", summe=" + summe +
                ", mittelwert=" + mittelwert +
                ", varianz=" + varianz +
                ", stdDev=" + stdDev +
                ", min=" + minimum +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Statistik statistik = (Statistik) o;

        if (!stichprobe.equals(statistik.stichprobe)) return false;
        if (!anzahl.equals(statistik.anzahl)) return false;
        if (!summe.equals(statistik.summe)) return false;
        if (!mittelwert.equals(statistik.mittelwert)) return false;
        if (!varianz.equals(statistik.varianz)) return false;
        if (!stdDev.equals(statistik.stdDev)) return false;
        return minimum.equals(statistik.minimum);
    }

    @Override
    public int hashCode() {
        int result = stichprobe.hashCode();
        result = 31 * result + anzahl.hashCode();
        result = 31 * result + summe.hashCode();
        result = 31 * result + mittelwert.hashCode();
        result = 31 * result + varianz.hashCode();
        result = 31 * result + stdDev.hashCode();
        result = 31 * result + minimum.hashCode();
        return result;
    }
}
