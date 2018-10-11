package de.maibornwolff.domainchoreography.exportdefinitions.model

import de.maibornwolff.domainchoreograph.exportDefinitions.model.utils.IdGenerator

data class ExportScope(
  val id: String = "Scope-" + generateId(),
  val executionContext: String,
  val nodes: MutableList<String> = mutableListOf()
) {
  fun addNode(exportNode: ExportNode) = this.nodes.add(exportNode.id)

  companion object : IdGenerator()
}
