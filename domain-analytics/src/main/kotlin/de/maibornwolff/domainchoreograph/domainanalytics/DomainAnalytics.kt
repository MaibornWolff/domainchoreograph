package de.maibornwolff.domainchoreograph.domainanalytics

import de.maibornwolff.domainchoreograph.analyticslogger.DomainAnalyticsLogger
import de.maibornwolff.domainchoreograph.analyticsserver.DomainAnalyticsServer

class DomainAnalytics(
  port: Int = 5400,
  activateLog: Boolean = false,
  openBrowserOnStart: Boolean = true
) {
  val server = DomainAnalyticsServer(
    port = port,
    openBrowserOnStart = openBrowserOnStart
  )

  val logger = DomainAnalyticsLogger(
    url = server.url,
    activateLog = activateLog
  )
}
