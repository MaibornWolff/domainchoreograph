package de.maibornwolff.domainchoreograph.examples.statistik;

import de.maibornwolff.domainchoreograph.examples.statistik.domaintypes.GesamtAuswertung;
import de.maibornwolff.domainchoreograph.examples.statistik.domaintypes.Statistik;
import de.maibornwolff.domainchoreograph.core.api.DomainEnvironment;
import de.maibornwolff.domainchoreograph.examples.statistik.choreographies.GesamtAuswertungCalculator;

public class Application {

    public static void main(String[] args) {
        DomainEnvironment environment = DomainEnvironment.builder()
            .addLogger(ChoreographyDevtool.domainAnalytics.getLogger())
            .build();
        GesamtAuswertungCalculator calculator = environment.get(GesamtAuswertungCalculator.class);
        GesamtAuswertung gesamtAuswertung = calculator.calculate(new StichprobenQuelle());

        for (Statistik statistik : gesamtAuswertung.getStatistikList()) {
            System.out.println(statistik);
            System.out.println("----");
        }

        ChoreographyDevtool.domainAnalytics.getServer().start();
    }
}
