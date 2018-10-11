package de.maibornwolff.domainchoreograph.examples.statistik.domaintypes;

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition;

import java.util.List;

@DomainDefinition
public interface StichprobenAdapter {

    List<Stichprobe> getStichproben();
}
