package de.maibornwolff.domainchoreograph.analyticsserver

import io.javalin.websocket.WsHandler

fun createWebSocketConfig(controller: WebSocketController) = { ws: WsHandler ->
  with(ws) {
    onConnect { session ->
      controller.registerSession(session)
    }

    onClose { session, _, reason ->
      controller.removeSession(session)
    }
  }
}
