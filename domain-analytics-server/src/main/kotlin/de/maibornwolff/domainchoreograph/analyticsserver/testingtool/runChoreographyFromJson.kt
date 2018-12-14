package de.maibornwolff.domainchoreograph.analyticsserver.testingtool

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.maibornwolff.domainchoreograph.core.api.DomainChoreographyOptions
import de.maibornwolff.domainchoreograph.core.processing.resolveDomainDefinitionWithOptions

fun runChoreographyFromJson(
    targetCanonicalName: String,
    inputs: Set<JsonEncodedClass>,
    options: DomainChoreographyOptions? = null
): Any {
    val targetClass = loadClassByName(targetCanonicalName)
    val inputs = inputs.map { it.asClass() }.toList()
    return resolveDomainDefinitionWithOptions(options, targetClass.kotlin, inputs)
}

class JsonEncodedClass(
    val canonicalName: String,
    val json: String
)

private val jsonMapper = jacksonObjectMapper()

private fun JsonEncodedClass.asClass(): Any {
    return jsonMapper.readValue(json, loadClassByName(canonicalName))
}

private fun loadClassByName(canonicalName: String): Class<*> {
    return jsonMapper::class.java.classLoader.loadClass(canonicalName)
}
