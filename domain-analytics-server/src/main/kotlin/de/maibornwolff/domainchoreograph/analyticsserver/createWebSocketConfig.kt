package de.maibornwolff.domainchoreograph.analyticsserver

import io.javalin.embeddedserver.jetty.websocket.WebSocketConfig

fun createWebSocketConfig(controller: WebSocketController) = WebSocketConfig { ws ->
  with(ws) {
    onConnect { session ->
      controller.registerSession(session)
    }

    onClose { session, _, reason ->
      controller.removeSession(session)
    }
  }
}
