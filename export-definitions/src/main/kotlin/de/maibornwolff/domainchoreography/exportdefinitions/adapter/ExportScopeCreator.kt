package de.maibornwolff.domainchoreography.exportdefinitions.adapter

import de.maibornwolff.domainchoreography.exportdefinitions.model.ExportExecutionContext
import de.maibornwolff.domainchoreography.exportdefinitions.model.ExportScope
import de.maibornwolff.domainchoreography.exportdefinitions.utils.UniqueIdGenerator

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
