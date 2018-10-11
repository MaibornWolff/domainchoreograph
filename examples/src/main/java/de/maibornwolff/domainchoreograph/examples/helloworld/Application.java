package de.maibornwolff.domainchoreograph.examples.helloworld;

import de.maibornwolff.domainchoreograph.DomainAnalytics;
import de.maibornwolff.domainchoreograph.core.api.DomainEnvironment;

public class Application {

    public static void main(String[] args) {

        DomainAnalytics domainAnalytics = new DomainAnalytics();
        domainAnalytics.getServer().start();

        Name name = new Name("Bob");

        DomainEnvironment environment = DomainEnvironment.builder()
            .addLogger(domainAnalytics.getLogger())
            .build();
        MyChoreography choreography = environment.get(MyChoreography.class);

        HelloWorld helloWorld = choreography.calculate(name);

        System.out.println(helloWorld.getMessage());
    }
}
