@file:JvmName("Application")

package de.maibornwolff.domainchoreograph.examples.statistic

import de.maibornwolff.domainchoreograph.core.api.DomainEnvironment
import de.maibornwolff.domainchoreograph.domainanalytics.DomainAnalytics
import de.maibornwolff.domainchoreograph.examples.statistic.choreographies.StatisticalEvaluationsCalculator

fun main(args: Array<String>) {
    // start server
    val analytics = DomainAnalytics()
    analytics.server.start()

    // setup environment
    val environment = DomainEnvironment.builder()
        .addLogger(analytics.logger)
        .build()

    // get generated Choreography implementation
    val statisticEvaluator = environment.get<StatisticalEvaluationsCalculator>()

    val sampleSource = SampleSource()
    val statisticalEvaluations = statisticEvaluator.evaluate(sampleSource)

    for (statistic in statisticalEvaluations.statistics) {
        println(statistic)
        println("----")
    }
}
