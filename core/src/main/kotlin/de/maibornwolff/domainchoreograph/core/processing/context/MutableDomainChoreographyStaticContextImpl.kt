package de.maibornwolff.domainchoreograph.core.processing.context

import de.maibornwolff.domainchoreograph.core.processing.dependencygraph.DependencyNode
import de.maibornwolff.domainchoreograph.core.api.DomainChoreographySchema
import de.maibornwolff.domainchoreograph.core.api.DomainContext
import de.maibornwolff.domainchoreograph.core.api.DomainContextNode
import de.maibornwolff.domainchoreograph.core.processing.utils.asJavaClass

class MutableDomainChoreographyStaticContextImpl(
    private val choreographyInterface: Class<*>?,
    private val schema: DomainChoreographySchema
) : MutableDomainChoreographyStaticContext, CastableToDomainChoreographyStaticContext {
    private val values: MutableMap<Class<*>, MutableDomainChoreographyStaticContextNode<Any>> = mutableMapOf()
    private var exception: Throwable? = null

    override fun toDomainChoreographyStaticContext(): DomainContext {
        updateNodeExceptionsBasedOnTheSubContext()
        return DomainContext(
            choreographyInterface = choreographyInterface,
            nodes = values.mapValues { it.value.toReadOnly() },
            exception = exception,
            schema = schema
        )
    }

    private fun updateNodeExceptionsBasedOnTheSubContext() {
        values.values.forEach {
            it.exception = getNodeException(it)
        }
    }

    private fun <T> getNodeException(node: MutableDomainChoreographyStaticContextNode<T>): Throwable? {
        return node.exception ?: node.subContextMapping
            .values
            .flatMap { it }
            .find { it.hasException() }
            ?.exception
    }

    override fun save(caller: Class<*>, value: Any) {
        if (values[caller] == null) {
            values[caller] = MutableDomainChoreographyStaticContextNode(value)
        } else {
            values[caller] = values[caller]!!.copy(value = value)
        }
    }

    override fun saveCalls(caller: Class<*>, choreographyClazz: Class<*>, calls: MutableList<DomainContext>) {
        val node = values[caller] ?: MutableDomainChoreographyStaticContextNode<Any>(null)
        node.subContextMapping[choreographyClazz] = calls
        values[caller] = node
    }

    override fun registerException(exception: Exception) {
        this.exception = exception

        val dependencyNodeWithException = findFirstNodeWithoutAValue()
        if (dependencyNodeWithException is DependencyNode.ChoreographyNode) {
            return
        }

        val nodeType = dependencyNodeWithException.domainType.asJavaClass()
        values[nodeType] = values[nodeType]?.copy(exception = exception)
            ?: MutableDomainChoreographyStaticContextNode<Any>(value = null, exception = exception)
    }

    private fun findFirstNodeWithoutAValue(): DependencyNode = schema.nodeOrder
        .find { values[it.domainType.asJavaClass()]?.value == null }!!
}

data class MutableDomainChoreographyStaticContextNode<T>(
    val value: T?,
    val subContextMapping: MutableMap<Class<*>, List<DomainContext>> = mutableMapOf(),
    var exception: Throwable? = null
) {
    fun toReadOnly(): DomainContextNode<T> {
        return DomainContextNode(
            value,
            subContextMapping,
            exception
        )
    }
}
