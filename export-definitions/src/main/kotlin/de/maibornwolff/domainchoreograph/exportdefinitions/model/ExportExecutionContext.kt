package de.maibornwolff.domainchoreograph.exportdefinitions.model

data class ExportExecutionContext(
  val id: String,
  val scopes: MutableList<String> = mutableListOf()
) {
  fun addScopeById(scopeId: String) {
    scopes.add(scopeId)
  }
}
