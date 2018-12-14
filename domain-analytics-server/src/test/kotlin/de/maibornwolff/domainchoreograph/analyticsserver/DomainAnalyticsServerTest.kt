package de.maibornwolff.domainchoreograph.analyticsserver

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.jackson.responseObject
import de.maibornwolff.domainchoreograph.analyticsserver.shared.MyDomainDefinition
import de.maibornwolff.domainchoreograph.analyticsserver.shared.Numbers
import de.maibornwolff.domainchoreograph.analyticsserver.shared.Sum
import de.maibornwolff.domainchoreograph.analyticsserver.testingtool.JsonEncodedClass
import de.maibornwolff.domainchoreograph.core.processing.dependencygraph.DependencyGraph
import de.maibornwolff.domainchoreograph.exportdefinitions.model.ExportGraph
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

val jsonMapper = ObjectMapper()

class DomainAnalyticsServerTest {
    val port = 3453
    val url = "http://localhost:$port"
    lateinit var server: DomainAnalyticsServer

    @Before
    fun setup() {
        server = DomainAnalyticsServer(
            port = port,
            openBrowserOnStart = false
        )
        server.start()
    }

    @After
    fun teardown() {
        server.stop()
    }

    @Test()
    fun `schema || should get`() {
        val (_, response, result) = "$url/schema/${MyDomainDefinition::class.java.canonicalName}"
            .httpGet()
            .responseString()

        assertThat(result.get()).isEqualTo("""
        {
          "${"$"}schema" : "http://json-schema.org/draft-04/schema#",
          "title" : "My Domain Definition",
          "type" : "object",
          "additionalProperties" : false,
          "properties" : { }
        }
        """.trimIndent())
        assertThat(response.statusCode).isEqualTo(200)
    }

    @Test()
    fun `domain-definitions || should get`() {
        val (_, response, result) = "$url/domain-definitions"
            .httpGet()
            .responseObject<List<String>>()

        assertThat(result.get()).contains(MyDomainDefinition::class.java.canonicalName)
        assertThat(response.statusCode).isEqualTo(200)
    }

    @Test()
    fun `graph || should get`() {
        val (_, response, result) = "$url/graph/${MyDomainDefinition::class.java.canonicalName}"
            .httpGet()
            .responseObject<ExportGraph>()

        assertThat(result.get()).isInstanceOf(ExportGraph::class.java)
        assertThat(response.statusCode).isEqualTo(200)
    }

    @Test()
    fun `run || should run choreography`() {
        val (_, response, result) = "$url/run"
            .httpPost()
            .jsonBody(jsonMapper.writeValueAsString(RunChoregraphyRequest(
                targetCanonicalName = Sum::class.java.canonicalName,
                inputs = setOf(
                    JsonEncodedClass(
                        canonicalName = Numbers::class.java.canonicalName,
                        json = jsonMapper.writeValueAsString(Numbers(
                            listOf(1, 2, 3)
                        ))
                    )
                )
            )))
            .responseObject<ExportGraph>()

        assertThat(result.get()).isInstanceOf(ExportGraph::class.java)
        assertThat(response.statusCode).isEqualTo(200)
    }
}
