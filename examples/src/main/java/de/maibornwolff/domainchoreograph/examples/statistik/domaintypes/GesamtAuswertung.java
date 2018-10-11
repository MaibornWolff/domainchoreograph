package de.maibornwolff.domainchoreograph.examples.statistik.domaintypes;

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition;
import de.maibornwolff.domainchoreograph.core.api.DomainFunction;
import de.maibornwolff.domainchoreograph.examples.statistik.choreographies.StatistikCalculator;

import java.util.List;
import java.util.stream.Collectors;

@DomainDefinition
public class GesamtAuswertung {

    private List<Statistik> statistikList;

    public GesamtAuswertung(List<Statistik> statistikList) {
        this.statistikList = statistikList;
    }

    @DomainFunction
    public static GesamtAuswertung resolve(StatistikCalculator statistikCalculator, StichprobenPool datenpool) {
        List<Statistik> statistikList = datenpool.getStichprobenList().stream()
            .map(statistikCalculator::calculate)
            .collect(Collectors.toList());

        return new GesamtAuswertung(statistikList);
    }

    public List<Statistik> getStatistikList() {
        return statistikList;
    }

    @Override
    public String toString() {
        return "Anzahl Gesamtauswertungen: " + statistikList.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GesamtAuswertung that = (GesamtAuswertung) o;

        return statistikList.equals(that.statistikList);
    }

    @Override
    public int hashCode() {
        return statistikList.hashCode();
    }
}
