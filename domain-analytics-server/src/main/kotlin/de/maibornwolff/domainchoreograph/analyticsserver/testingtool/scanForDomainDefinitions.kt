package de.maibornwolff.domainchoreograph.analyticsserver.testingtool

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import io.github.classgraph.ClassGraph

fun scanForDomainDefinitions(): Set<Class<*>> {
    val result = ClassGraph()
        .enableClassInfo()
        .enableAnnotationInfo()
        .whitelistPackages("de.maibornwolff.domainchoreograph")
        .scan()
        .getClassesWithAnnotation(DomainDefinition::class.java.canonicalName)

    return result.map { it.loadClass() }.toSet()
}
