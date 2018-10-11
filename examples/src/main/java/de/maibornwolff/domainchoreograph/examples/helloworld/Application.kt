package de.maibornwolff.domainchoreograph.examples.helloworld

import de.maibornwolff.domainchoreograph.core.api.DomainEnvironment
import de.maibornwolff.domainchoreograph.domainanalytics.DomainAnalytics

object Application {

    @JvmStatic
    fun main(args: Array<String>) {

        // start analytics server
        val domainAnalytics = DomainAnalytics()
        domainAnalytics.server.start()

        // setup environment
        val environment = DomainEnvironment.builder()
            .addLogger(domainAnalytics.logger)
            .build()

        // input parameter
        val name = Name("Bob")

        // get generated implementation
        val choreography: HelloWorldChoreography = environment.get()
        val helloWorld = choreography.calculate(name)

        println(helloWorld.message)
    }
}
