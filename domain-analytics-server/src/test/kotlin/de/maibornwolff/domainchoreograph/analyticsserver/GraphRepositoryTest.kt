package de.maibornwolff.domainchoreograph.analyticsserver

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import de.maibornwolff.domainchoreograph.analyticsserver.events.EventListenerCallback
import de.maibornwolff.domainchoreograph.exportdefinitions.model.ExportGraph
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

internal class GraphRepositoryTest {
  lateinit var repository: GraphRepository

  @Before
  fun beforeEach() {
    repository = GraphRepository()
  }

  @Test
  fun addGraph() {
    val mockListener: EventListenerCallback<ExportGraph> = mock()

    repository.onAddGraph.subscribe(mockListener)
    repository.addGraph(mockGraph)

    assertThat(repository.graphs).isEqualTo(listOf(mockGraph))
    verify(mockListener).invoke(mockGraph)
  }
}
