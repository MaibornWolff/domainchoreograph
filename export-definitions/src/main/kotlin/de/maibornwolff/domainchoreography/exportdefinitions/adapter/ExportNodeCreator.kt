package de.maibornwolff.domainchoreography.exportdefinitions.adapter

import de.maibornwolff.domainchoreography.exportdefinitions.model.ExportNode
import de.maibornwolff.domainchoreography.exportdefinitions.model.ExportScope
import de.maibornwolff.domainchoreography.exportdefinitions.model.toExportException
import de.maibornwolff.domainchoreography.exportdefinitions.utils.UniqueIdGenerator
import de.maibornwolff.domainchoreography.exportdefinitions.utils.firstLetterLowercase

internal class ExportNodeCreator(
    private val idGenerator: UniqueIdGenerator,
    private val scope: ExportScope
) {
    fun create(type: Class<*>, name: String, value: Any?, exception: Throwable?): ExportNode {
        val nodeId = idGenerator.createUniqueId(getIdBase(name.firstLetterLowercase()))
        scope.nodes.add(nodeId)
        return ExportNode(
            id = nodeId,
            name = name,
            value = value,
            type = type,
            scope = scope.id,
            exception = exception?.toExportException()
        )
    }

    private fun getIdBase(name: String) = if (scope.id == "root") {
        name
    } else {
        "${scope.id}::$name"
    }
}
