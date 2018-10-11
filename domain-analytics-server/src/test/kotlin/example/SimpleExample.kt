package example

import de.maibornwolff.domainchoreograph.analyticsserver.DomainAnalyticsServer

fun main(args: Array<String>) {
  val server = DomainAnalyticsServer(openBrowserOnStart = false)
  server.start()
}
