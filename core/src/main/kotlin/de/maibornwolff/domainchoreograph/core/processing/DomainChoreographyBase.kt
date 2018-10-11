package de.maibornwolff.domainchoreograph.core.processing

import de.maibornwolff.domainchoreograph.core.api.DomainChoreographyMeta
import de.maibornwolff.domainchoreograph.core.api.DomainChoreographyOptions
import de.maibornwolff.domainchoreograph.core.processing.context.MutableDomainChoreographyStaticContext
import de.maibornwolff.domainchoreograph.core.processing.context.MutableDomainChoreographyStaticContextImpl
import de.maibornwolff.domainchoreograph.core.processing.context.MutableDomainChoreographyStaticContextStub

abstract class DomainChoreographyBase(
    private val choreographyInterface: Class<*>,
    protected val options: DomainChoreographyOptions? = null
) {
    abstract val meta: DomainChoreographyMeta

    protected fun <T> run(methodName: String, choreography: (context: MutableDomainChoreographyStaticContext) -> T): T {
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
