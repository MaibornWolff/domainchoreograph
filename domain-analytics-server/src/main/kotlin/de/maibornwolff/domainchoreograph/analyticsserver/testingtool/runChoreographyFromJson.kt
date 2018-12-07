package de.maibornwolff.domainchoreograph.analyticsserver.testingtool

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.maibornwolff.domainchoreograph.core.processing.resolveDomainDefinition

fun runChoreographyFromJson(targetCanonicalName: String, inputs: Set<JsonEncodedClass>): Any {
    val targetClass = loadClassByName(targetCanonicalName)
    val inputs = inputs.map { it.asClass() }.toList()
    return resolveDomainDefinition(targetClass.kotlin, inputs)
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
