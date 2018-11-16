package de.maibornwolff.domainchoreograph.exportdefinitions.adapter

import de.maibornwolff.domainchoreograph.exportdefinitions.model.ExportExecutionContext
import de.maibornwolff.domainchoreograph.exportdefinitions.model.ExportNode

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
