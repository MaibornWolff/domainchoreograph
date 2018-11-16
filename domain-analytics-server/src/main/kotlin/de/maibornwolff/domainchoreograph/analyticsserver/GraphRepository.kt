package de.maibornwolff.domainchoreograph.analyticsserver
import de.maibornwolff.domainchoreograph.analyticsserver.events.EventEmitter
import de.maibornwolff.domainchoreograph.analyticsserver.events.Subscribable
import de.maibornwolff.domainchoreograph.exportdefinitions.model.ExportGraph

class GraphRepository {
  private val graphsStore: MutableList<ExportGraph> = mutableListOf()

  private val addGraphEmitter = EventEmitter<ExportGraph>()
  val onAddGraph: Subscribable<ExportGraph> = addGraphEmitter

  val graphs: List<ExportGraph> = graphsStore

  fun addGraph(graph: ExportGraph) = graphsStore
    .add(graph)
    .also { addGraphEmitter.emit(graph) }
}
