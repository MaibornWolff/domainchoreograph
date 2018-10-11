package de.maibornwolff.domainchoreograph.analyticsserver

import de.maibornwolff.domainchoreography.exportdefinitions.utils.toJson
import org.eclipse.jetty.websocket.api.Session

class WebSocketController(
  private val repository: GraphRepository
) {
  private val sessions: MutableSet<Session> = mutableSetOf()

  init {
    broadcastAddGraphEvents()
  }

  private fun broadcastAddGraphEvents() {
    this.repository.onAddGraph.subscribe { broadcast(it.toJson()) }
  }

  /** Broadcast a message to all clients */
  fun broadcast(message: String) {
    try {
    sessions
      .filter { it.isOpen }
      .forEach { it.remote.sendString(message) }
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  /** Register a new session */
  fun registerSession(session: Session) {
    sessions.add(session)
    sendLatestGraphToClient(session)
  }

  /** Remove a registered session */
  fun removeSession(session: Session) {
    sessions.remove(session)
  }

  private fun sendLatestGraphToClient(session: Session) {
    if (repository.graphs.isEmpty()) {
      return
    }
    session.remote.sendString(repository.graphs.last().toJson())
  }
}
