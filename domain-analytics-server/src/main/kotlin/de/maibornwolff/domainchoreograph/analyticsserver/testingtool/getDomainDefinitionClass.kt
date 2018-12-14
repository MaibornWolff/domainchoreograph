package de.maibornwolff.domainchoreograph.analyticsserver.testingtool

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import io.github.classgraph.ClassGraph

fun getDomainDefinitionClass(name: String): Class<*>? {
    val result = ClassGraph()
        .enableClassInfo()
        .enableAnnotationInfo()
        .scan()
        .getClassesWithAnnotation(DomainDefinition::class.java.canonicalName)
        .filter { it.name == name }

    return result.firstOrNull()?.loadClass()
}
