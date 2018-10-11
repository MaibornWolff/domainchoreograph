package de.maibornwolff.domainchoreograph.examples.statistik.choreographies;

import de.maibornwolff.domainchoreograph.examples.statistik.StichprobenQuelle;
import de.maibornwolff.domainchoreograph.examples.statistik.domaintypes.GesamtAuswertung;
import de.maibornwolff.domainchoreograph.core.api.DomainChoreography;

@DomainChoreography
public interface GesamtAuswertungCalculator {
    GesamtAuswertung calculate(StichprobenQuelle quelle);
}
