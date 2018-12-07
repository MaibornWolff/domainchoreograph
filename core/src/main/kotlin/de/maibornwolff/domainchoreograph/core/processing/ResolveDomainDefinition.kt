package de.maibornwolff.domainchoreograph.core.processing

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asClassName
import de.maibornwolff.domainchoreograph.core.api.DomainChoreographyMeta
import de.maibornwolff.domainchoreograph.core.api.DomainChoreographyOptions
import de.maibornwolff.domainchoreograph.core.api.DomainChoreographySchema
import de.maibornwolff.domainchoreograph.core.api.DomainContext
import de.maibornwolff.domainchoreograph.core.processing.dependencygraph.DependencyGraph
import de.maibornwolff.domainchoreograph.core.processing.dependencygraph.DependencyNode
import de.maibornwolff.domainchoreograph.core.processing.reflection.KReflectionVariable
import de.maibornwolff.domainchoreograph.core.processing.reflection.ReflectionVariable
import de.maibornwolff.domainchoreograph.core.processing.reflection.asReflectionType
import de.maibornwolff.domainchoreograph.core.processing.utils.asJavaClass
import java.lang.reflect.InvocationHandler
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.companionObjectInstance

private typealias References = MutableMap<ClassName, Any>

fun <T : Any> resolveDomainDefinition(target: KClass<T>, params: List<Any>): T {
    return resolveDomainDefinitionWithOptions(null, target, params)
}

fun <T : Any> resolveDomainDefinitionWithOptions(
    options: DomainChoreographyOptions?,
    target: KClass<T>,
    params: List<Any>,
    choreographyInterface: Class<*>? = null
): T {
    val graph = DependencyGraph.create(
        target.asReflectionType(),
        params.asReflectionVariables()
    )
    val runner = DomainChoreographyRunner(
        meta = object : DomainChoreographyMeta {
            override val schemas = mapOf(
                graph.target.name to DomainChoreographySchema(
                    rootNode = graph.target,
                    nodeOrder = graph.nodes
                )
            )
        },
        choreographyInterface = choreographyInterface,
        options = options
    )

    val references: References = params
        .map { it::class.asClassName() to it }
        .toMap()
        .toMutableMap()

    runner.run(graph.target.name) { context ->
        params.forEach { context.save(it::class.java, it) }
        graph.nodes.forEach {
            when (it) {
                is DependencyNode.FunctionNode -> {
                    try {
                        val result = it.invoke(references)
                        references[it.domainType] = result
                        context.save(it.domainType.asJavaClass(), result)
                    } catch (e: Exception) {
                        context.registerException(e)
                        throw e
                    }
                }
                is DependencyNode.ChoreographyNode -> {
                    val calls = mutableListOf<DomainContext>()
                    context.saveCalls(
                        it.caller.asJavaClass(),
                        it.domainType.asJavaClass(),
                        calls
                    )

                    val result = it.resolve(DomainChoreographyOptions(calls = calls))
                    references[it.domainType] = result
                    context.save(it.domainType.asJavaClass(), result)
                }
            }
        }
    }
    return references[target.asClassName()] as T
}

private fun List<Any>.asReflectionVariables(): List<ReflectionVariable> =
    mapIndexed { index, param ->
        KReflectionVariable(
            name = "Parameter $index",
            type = param::class.asReflectionType()
        )
    }

private fun DependencyNode.FunctionNode.invoke(references: References): Any {
    val callerClass = caller.asJavaClass()
    val companion = callerClass.kotlin.companionObject!!.java
    val method = companion.methods
        .find { it.name == name }
        ?: throw NullPointerException("Method '$name' not found on '${callerClass.canonicalName}'")
    try {
        return method.invoke(callerClass.kotlin.companionObjectInstance, *references.resolve(parameters))
    } catch (e: InvocationTargetException) {
        throw e.targetException
    }
}

private fun References.resolve(params: List<DependencyNode>) = params
    .map { this[it.type]!! }
    .toTypedArray()

private fun DependencyNode.ChoreographyNode.resolve(options: DomainChoreographyOptions?): Any {
    val choreographyInterface = type.asJavaClass()
    return Proxy.newProxyInstance(
        choreographyInterface.classLoader,
        arrayOf(choreographyInterface),
        ChoreographyInvocationHandler(
            choreographyInterface,
            options
        )
    )
}

private class ChoreographyInvocationHandler(
    private val clazz: Class<*>,
    private val options: DomainChoreographyOptions?
) : InvocationHandler {
    override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any {
        return resolveDomainDefinitionWithOptions(
            options = options,
            target = method.returnType.kotlin,
            params = args?.toList() ?: listOf(),
            choreographyInterface = clazz
        )
    }
}
