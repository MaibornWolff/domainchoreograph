@file:JvmName("DevtoolServerMain")

package de.maibornwolff.domainchoreograph.analyticsserver

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.maibornwolff.domainchoreograph.analyticsserver.testingtool.ContextLogger
import de.maibornwolff.domainchoreograph.analyticsserver.testingtool.getDomainDefinitionClass
import de.maibornwolff.domainchoreograph.analyticsserver.testingtool.runChoreographyFromJson
import de.maibornwolff.domainchoreograph.analyticsserver.testingtool.scanForDomainDefinitions
import de.maibornwolff.domainchoreograph.core.api.DomainChoreographyOptions
import de.maibornwolff.domainchoreograph.core.processing.dependencygraph.DependencyGraph
import de.maibornwolff.domainchoreograph.core.processing.reflection.asReflectionType
import de.maibornwolff.domainchoreograph.exportdefinitions.adapter.asExportGraph
import de.maibornwolff.domainchoreograph.exportdefinitions.model.ExportGraph
import getJsonSchemaFromClass
import io.javalin.Context
import io.javalin.Javalin
import java.awt.Desktop
import java.net.URI

private val jsonMapper = jacksonObjectMapper()

fun main(args: Array<String>) {
    DomainAnalyticsServer(openBrowserOnStart = false).start()
}

class DomainAnalyticsServer(
    var port: Int = 5400,
    var openBrowserOnStart: Boolean = true
) {
    val url: String
        get() = "http://localhost:$port"
    private var repository = GraphRepository()
    private val app: Javalin = Javalin.create()
        .requestLogger { ctx, timeMs ->
            println("${ctx.method()} ${ctx.path()} took $timeMs ms")
        }
        .enableCorsForAllOrigins()

    fun start() {
        setup()
        setupWebsocket()
        serveStaticFiles()
        listenToIncomingGraphs()
        serveIndexHtmlForRemainingRequests()
        setupPlaygroundApi()
        app.start()
        openBrowserIfConfigured()
        app.exception(Exception::class.java) { e, ctx ->
            e.printStackTrace()
            ctx.status(500)
            ctx.result(e.message ?: "")
        }
        println("Started debug server on port $port")
    }

    fun stop() {
        app.stop()
    }

    private fun setup() {
        repository = GraphRepository()
        app.port(port)
    }

    private fun setupWebsocket() {
        val controller = WebSocketController(repository)
        app.ws("/graph", createWebSocketConfig(controller))
    }

    private fun serveStaticFiles() {
        app.enableStaticFiles("/webapp")
    }

    private fun listenToIncomingGraphs() {
        app.post("/graph") { ctx ->
            ctx.resolveJsonBody<ExportGraph> { graph ->
                repository.addGraph(graph)
                ctx.status(204)
            }
        }
    }

    private fun serveIndexHtmlForRemainingRequests() {
        app.error(404) { ctx ->
            ctx.status(200)
            ctx.header("Content-Type", "text/html")
            ctx.result(DomainAnalyticsServer::class.java.getResource("/webapp/index.html").readText())
        }
    }

    private fun setupPlaygroundApi() {
        app.get("/schema/:java-class-name") { ctx ->
            val javaClassName = ctx.pathParam("java-class-name")
            val javaClass = getDomainDefinitionClass(javaClassName)
            if (javaClass == null) {
                ctx.status(404)
                ctx.result("Not Found")
                return@get
            }
            ctx.status(200)
            ctx.contentType("application/json")
            ctx.result(getJsonSchemaFromClass(javaClass))
        }

        app.get("/domain-definitions") { ctx ->
            val domainDefinitionsClasses = scanForDomainDefinitions().map { it.canonicalName }

            ctx.status(200)
            ctx.json(domainDefinitionsClasses)
        }

        app.get("/graph/:target-java-class-name") { ctx ->
            val javaClassName = ctx.pathParam("target-java-class-name")
            val clazz = getDomainDefinitionClass(javaClassName)!!
            val graph = DependencyGraph.create(
                clazz.kotlin.asReflectionType(),
                listOf(),
                unsafe = true
            )
            val exportGraph = graph.asExportGraph()

            ctx.status(200)
            ctx.json(exportGraph)
        }

        app.post("/run") { ctx ->
            val logger = ContextLogger()
            ctx.resolveJsonBody<RunChoregraphyRequest> {
                runChoreographyFromJson(
                    it.targetCanonicalName,
                    it.inputs,
                    options = DomainChoreographyOptions(logger = setOf(logger))
                )
            }

            ctx.status(200)
            ctx.json(logger.context!!.asExportGraph())
        }
    }

    private fun openBrowserIfConfigured() {
        if (openBrowserOnStart) {
            openBrowser()
        }
    }

    private fun openBrowser() {
        try {
            Desktop.getDesktop().browse(URI(url))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

private inline fun <reified T : Any> Context.resolveJsonBody(handle: (value: T) -> Unit) {
    try {
        handle(jsonMapper.readValue(body()))
    } catch (e: Exception) {
        e.printStackTrace()
        status(400)
        result("""{ "message": "Invalid json" }""")
    }
}
