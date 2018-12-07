package de.maibornwolff.domainchoreograph.core.processing

import de.maibornwolff.domainchoreograph.core.api.DomainChoreographyMeta
import de.maibornwolff.domainchoreograph.core.api.DomainChoreographyOptions
import de.maibornwolff.domainchoreograph.core.processing.context.MutableDomainChoreographyStaticContext
import de.maibornwolff.domainchoreograph.core.processing.context.MutableDomainChoreographyStaticContextImpl
import de.maibornwolff.domainchoreograph.core.processing.context.MutableDomainChoreographyStaticContextStub

class DomainChoreographyRunner(
    private val meta: DomainChoreographyMeta,
    private val choreographyInterface: Class<*>?,
    private val options: DomainChoreographyOptions? = null
) {
    fun <T> run(methodName: String, choreography: (context: MutableDomainChoreographyStaticContext) -> T): T {
        return if (options == null) {
            runWithoutContext(choreography)
        } else {
            runWithContext(methodName, choreography)
        }
    }

    private fun <T> runWithContext(methodName: String, choreography: (context: MutableDomainChoreographyStaticContext) -> T): T {
        val context = MutableDomainChoreographyStaticContextImpl(
            choreographyInterface = choreographyInterface,
            schema = meta.schemas[methodName]!!
        )

        try {
            val result = choreography(context)

            options?.resolve(context)
            return result
        } catch (e: Exception) {
            context.registerException(e)
            options?.resolve(context)
            throw e
        }
    }

    private fun <T> runWithoutContext(choreography: (context: MutableDomainChoreographyStaticContext) -> T): T {
        val context = MutableDomainChoreographyStaticContextStub()
        return choreography(context)
    }
}
