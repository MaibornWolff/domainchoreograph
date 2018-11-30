package de.maibornwolff.domainchoreograph.core.processing

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asClassName
import de.maibornwolff.domainchoreograph.core.processing.dependencygraph.DependencyGraph
import de.maibornwolff.domainchoreograph.core.processing.dependencygraph.DependencyNode
import de.maibornwolff.domainchoreograph.core.processing.reflection.KReflectionVariable
import de.maibornwolff.domainchoreograph.core.processing.reflection.ReflectionVariable
import de.maibornwolff.domainchoreograph.core.processing.reflection.asReflectionType
import de.maibornwolff.domainchoreograph.core.processing.utils.asJavaClass
import java.lang.NullPointerException
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.companionObjectInstance

private typealias References = MutableMap<ClassName, Any>

fun <T : Any> resolveDomainDefinition(target: KClass<T>, params: List<Any>): T {
    val graph = DependencyGraph.create(
        target.asReflectionType(),
        params.asReflectionVariables()
    )

    val references: References = params
        .map { it::class.asClassName() to it }
        .toMap()
        .toMutableMap()

    graph.nodes.forEach {
        when (it) {
            is DependencyNode.FunctionNode -> {
                references[it.domainType] = it.invoke(references)
            }
            is DependencyNode.ChoreographyNode -> {
            }
        }
    }

    return references[target.asClassName()] as T
}

private fun List<Any>.asReflectionVariables(): List<ReflectionVariable> =
    mapIndexed { index, param ->
        KReflectionVariable(
            name = "Param $index",
            type = param::class.asReflectionType()
        )
    }

private fun DependencyNode.FunctionNode.invoke(references: References): Any {
    val callerClass = caller.asJavaClass()
    val companion = callerClass.kotlin.companionObject!!.java
    val method = companion.methods
        .find { it.name == name }
        ?: throw NullPointerException("Method '$name' not found on '${callerClass.canonicalName}'")
    return method.invoke(callerClass.kotlin.companionObjectInstance, *references.resolve(parameters))
}

private fun References.resolve(params: List<DependencyNode>) = params
    .map { this[it.type]!! }
    .toTypedArray()

