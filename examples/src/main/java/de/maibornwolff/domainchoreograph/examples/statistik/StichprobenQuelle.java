package de.maibornwolff.domainchoreograph.examples.statistik;

import de.maibornwolff.domainchoreograph.examples.statistik.domaintypes.Stichprobe;
import de.maibornwolff.domainchoreograph.examples.statistik.domaintypes.StichprobenAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StichprobenQuelle implements StichprobenAdapter {

    @Override
    public List<Stichprobe> getStichproben() {
        return generateStichprobenPool();
    }

    public static List<Stichprobe> generateStichprobenPool() {
        List<Stichprobe> stichproben = new ArrayList<>();
        stichproben.add(new Stichprobe(Arrays.asList(1, 2, 3, 4, 5)));
        stichproben.add(new Stichprobe(Arrays.asList(5, 9, 4, 3, 1)));
        stichproben.add(new Stichprobe(Arrays.asList(4, 4, 5)));

        return stichproben;
    }

    @Override
    public String toString() {
        return "(StichprobenQuelle)";
    }
}
