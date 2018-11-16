package de.maibornwolff.domainchoreograph.analyticslogger

import de.maibornwolff.domainchoreograph.core.api.DomainContext
import de.maibornwolff.domainchoreograph.core.api.DomainLogger
import de.maibornwolff.domainchoreograph.exportdefinitions.adapter.asExportGraph
import de.maibornwolff.domainchoreograph.exportdefinitions.utils.toJson
import kotlinx.coroutines.experimental.launch
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder

class DomainAnalyticsLogger(
    private val url: String,
    private val activateLog: Boolean = false,
    private val asynchronousShipping: Boolean = true
) : DomainLogger {
    private val httpClient = HttpClientBuilder.create().build()

    override fun onComplete(context: DomainContext) {
        if (asynchronousShipping) {
            launch { shipResultToServer(context) }
        } else {
            shipResultToServer(context)
        }

    }

    private fun shipResultToServer(context: DomainContext) {
        val graph = context.asExportGraph()

        try {
            val request = createHttpPost(graph.toJson())
            val response = httpClient.execute(request)

            if (response.statusLine.statusCode == 204) {
                log("Result ${graph.id} sent to $url")
            } else {
                log("Unable to send result ${graph.id} to $url (Server response: ${response.statusLine.statusCode})")
            }
        } catch (exception: Exception) {
            log("Unable to send ${graph.id} to $url due to ${exception.message}")
        }
    }

    private fun createHttpPost(resultJson: String): HttpPost {
        val request = HttpPost("$url/graph")
        request.addHeader("content-type", "application/json")
        val params = StringEntity(resultJson, "UTF-8")
        request.entity = params
        return request
    }

    private fun log(message: String) {
        if (activateLog) {
            println(message)
        }
    }
}
