package de.maibornwolff.domainchoreograph.core.processing

import com.squareup.kotlinpoet.TypeName
import de.maibornwolff.domainchoreograph.core.processing.dependencygraph.DependencyGraph

class Method(
    val name: String,
    val parameters: List<Parameter>,
    val returnType: TypeName,
    val dependencyGraph: DependencyGraph
)
