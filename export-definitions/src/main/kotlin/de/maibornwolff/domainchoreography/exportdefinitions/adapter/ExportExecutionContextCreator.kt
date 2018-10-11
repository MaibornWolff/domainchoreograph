package de.maibornwolff.domainchoreography.exportdefinitions.adapter

import de.maibornwolff.domainchoreography.exportdefinitions.model.ExportExecutionContext
import de.maibornwolff.domainchoreography.exportdefinitions.model.ExportNode

internal class ExportExecutionContextCreator(
    private val node: ExportNode
) {

    fun create(): ExportExecutionContext {
        return ExportExecutionContext(
            id = node.id,
            scopes = mutableListOf()
        )
    }
}
