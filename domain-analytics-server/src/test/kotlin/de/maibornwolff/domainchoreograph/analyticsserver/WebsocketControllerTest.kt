package de.maibornwolff.domainchoreograph.analyticsserver

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import de.maibornwolff.domainchoreograph.exportdefinitions.utils.toJson
import org.eclipse.jetty.websocket.api.Session
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`

internal class WebsocketControllerTest {
  private lateinit var repository: GraphRepository
  private lateinit var controller: WebSocketController

  @Before
  fun beforeEach() {
    repository = GraphRepository()
    controller = WebSocketController(repository)
  }

  @Test
  fun broadcast() {
    val mockSessions: List<Session> = List(10) { mockSession() }
    val expectedMessage = "Message"

    mockSessions.forEach { controller.registerSession(it) }
    controller.broadcast(expectedMessage)

    mockSessions.forEach {
      verify(it.remote).sendString(expectedMessage)
    }
  }

  @Test
  fun broadcastOnAddGraph() {
    val mockSessions: List<Session> = List(10) { mockSession() }
    val expectedGraph = mockGraph

    mockSessions.forEach { controller.registerSession(it) }

    repository.addGraph(expectedGraph)

    mockSessions.forEach {
      verify(it.remote).sendString(expectedGraph.toJson())
    }
  }

  @Test
  fun sendLastGraphToNewSessions() {
    val mockSessions: List<Session> = List(10) { mockSession() }
    val expectedGraph = mockGraph

    repository.addGraph(expectedGraph)
    mockSessions.forEach { controller.registerSession(it) }

    mockSessions.forEach {
      verify(it.remote).sendString(expectedGraph.toJson())
    }
  }
}

fun mockSession(): Session {
  val mockSession: Session = mock()
  `when`(mockSession.remote).thenReturn(mock())
  `when`(mockSession.isOpen).thenReturn(true)
  return mockSession
}
