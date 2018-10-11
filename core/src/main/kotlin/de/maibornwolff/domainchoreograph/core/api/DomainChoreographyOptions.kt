package de.maibornwolff.domainchoreograph.core.api

import de.maibornwolff.domainchoreograph.core.processing.context.CastableToDomainChoreographyStaticContext
import de.maibornwolff.domainchoreograph.core.processing.context.MutableDomainChoreographyStaticContext

class DomainChoreographyOptions(
    val logger: Set<DomainLogger> = setOf(),
    val calls: MutableList<DomainContext>? = null
) {
    fun resolve(context: MutableDomainChoreographyStaticContext) {
        if (context !is CastableToDomainChoreographyStaticContext) {
            return
        }
        val readOnlyContext = context.toDomainChoreographyStaticContext()
        calls?.add(readOnlyContext)
        logger.forEach { it.onComplete(readOnlyContext) }
    }
}
