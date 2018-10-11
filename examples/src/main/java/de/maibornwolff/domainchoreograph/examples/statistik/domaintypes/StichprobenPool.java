package de.maibornwolff.domainchoreograph.examples.statistik.domaintypes;

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition;
import de.maibornwolff.domainchoreograph.core.api.DomainFunction;

import java.util.List;

@DomainDefinition
public class StichprobenPool {

    private List<Stichprobe> stichprobenList;

    public StichprobenPool(List<Stichprobe> input) {
        this.stichprobenList = input;
    }

    @DomainFunction
    public static StichprobenPool resolveStichproben(StichprobenAdapter stichprobenAdapter) {
        List<Stichprobe> stichprobenList = stichprobenAdapter.getStichproben();
        return new StichprobenPool(stichprobenList);
    }

    public List<Stichprobe> getStichprobenList() {
        return this.stichprobenList;
    }

    @Override
    public String toString() {
        return "Anzahl Stichproben: " + stichprobenList.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StichprobenPool that = (StichprobenPool) o;

        return stichprobenList.equals(that.stichprobenList);
    }

    @Override
    public int hashCode() {
        return stichprobenList.hashCode();
    }
}
