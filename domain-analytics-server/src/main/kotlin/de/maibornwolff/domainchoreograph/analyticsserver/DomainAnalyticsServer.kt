@file:JvmName("DevtoolServerMain")
package de.maibornwolff.domainchoreograph.analyticsserver

import de.maibornwolff.domainchoreography.exportdefinitions.utils.jsonToExportGraph
import io.javalin.Javalin
import java.awt.Desktop
import java.net.URI

fun main(args : Array<String>) {
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

  fun start() {
    setup()
    setupWebsocket()
    serveStaticFiles()
    listenToIncomingGraphs()
    serveIndexHtmlForRemainingRequests()
    app.start()
    openBrowserIfConfigured()
    println("Started debug server on port $port")
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
      val graph = try {
        jsonToExportGraph(ctx.body())
      } catch (e: Exception) {
        e.printStackTrace()
        ctx.status(400)
        ctx.result("""{ "message": "Invalid json" }""")
        return@post
      }
      repository.addGraph(graph)

      ctx.status(204)
    }
  }

  private fun serveIndexHtmlForRemainingRequests() {
    app.error(404) { ctx ->
      ctx.status(200)
      ctx.header("Content-Type", "text/html")
      ctx.result(DomainAnalyticsServer::class.java.getResource("/webapp/index.html").readText())
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
