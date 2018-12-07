package de.maibornwolff.domainchoreograph.core.api

data class DomainContextNode<T>(
    val value: T?,
    val subContextMapping: Map<Class<*>, List<DomainContext>> = mapOf(),
    val exception: Throwable? = null
)

data class DomainContext(
    val choreographyInterface: Class<*>?,
    val schema: DomainChoreographySchema,
    val nodes: Map<Class<*>, DomainContextNode<*>>,
    val exception: Throwable? = null
) {
    fun hasException() = exception != null
}
