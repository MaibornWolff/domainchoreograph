package de.maibornwolff.domainchoreograph.examples.statistik.orchestration;

import de.maibornwolff.domainchoreograph.examples.statistik.domaintypes.*;

import java.util.ArrayList;

public class StatistikService {

    public static GesamtAuswertung calculateGesamtAuswertung(StichprobenPool datenpool) {

        ArrayList<Statistik> statistikList = new ArrayList<>();

        for (Stichprobe stichprobe : datenpool.getStichprobenList()) {

            Anzahl anzahl = Anzahl.resolve(stichprobe);
            Summe summe = Summe.resolve(stichprobe);
            Mittelwert mittelwert = Mittelwert.Companion.resolve(anzahl, summe);
            Varianz varianz = Varianz.resolve(mittelwert, stichprobe, anzahl);
            StdDev stdDev = StdDev.resolve(varianz);
            Integer minimum = MinimumFactory.minimum(stichprobe);
            Statistik statistik = Statistik.resolve(stichprobe, anzahl, summe, mittelwert, varianz, stdDev, minimum);

            statistikList.add(statistik);
        }

        GesamtAuswertung gesamtAuswertung = new GesamtAuswertung(statistikList);

        return gesamtAuswertung;
    }

}
