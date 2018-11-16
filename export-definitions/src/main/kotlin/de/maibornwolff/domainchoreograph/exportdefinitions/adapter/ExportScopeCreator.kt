package de.maibornwolff.domainchoreograph.exportdefinitions.adapter

import de.maibornwolff.domainchoreograph.exportdefinitions.model.ExportExecutionContext
import de.maibornwolff.domainchoreograph.exportdefinitions.model.ExportScope
import de.maibornwolff.domainchoreograph.exportdefinitions.utils.UniqueIdGenerator

internal class ExportScopeCreator(
    private val idGenerator: UniqueIdGenerator,
    private val parentContext: ExportExecutionContext
) {
    fun create(): ExportScope {
        val id = idGenerator.createUniqueId("${parentContext.id}::")
        parentContext.addScopeById(id)
        return ExportScope(
            id = id,
            executionContext = parentContext.id,
            nodes = mutableListOf()
        )
    }
}
