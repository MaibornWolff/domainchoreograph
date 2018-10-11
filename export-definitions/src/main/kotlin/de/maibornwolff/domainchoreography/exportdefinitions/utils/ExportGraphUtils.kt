package de.maibornwolff.domainchoreography.exportdefinitions.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.maibornwolff.domainchoreography.exportdefinitions.model.ExportGraph

private val jsonMapper = jacksonObjectMapper()

fun jsonToExportGraph(json: String): ExportGraph = jsonMapper.readValue(json)

fun ExportGraph.toJson(): String {
    return jsonMapper
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(this)
}
