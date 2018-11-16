package de.maibornwolff.domainchoreograph.exportdefinitions.model

data class ExportGraph(
    val id: String,
    val nodes: Map<String, ExportNode>,
    val dependencies: List<ExportDependency>,
    val scopes: Map<String, ExportScope>,
    val executionContexts: Map<String, ExportExecutionContext>
)
