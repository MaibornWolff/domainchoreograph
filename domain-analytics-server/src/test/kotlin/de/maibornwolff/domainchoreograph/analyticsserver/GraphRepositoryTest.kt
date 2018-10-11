package de.maibornwolff.domainchoreograph.analyticsserver

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import de.maibornwolff.domainchoreograph.analyticsserver.events.EventListenerCallback
import de.maibornwolff.domainchoreography.exportdefinitions.model.ExportGraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GraphRepositoryTest {
  lateinit var repository: GraphRepository

  @BeforeEach
  fun beforeEach() {
    repository = GraphRepository()
  }

  @Test
  fun addGraph() {
    val mockListener: EventListenerCallback<ExportGraph> = mock()

    repository.onAddGraph.subscribe(mockListener)
    repository.addGraph(mockGraph)

    assertEquals(repository.graphs, listOf(mockGraph))
    verify(mockListener).invoke(mockGraph)
  }
}
