package de.maibornwolff.domainchoreograph.core.processing

import de.maibornwolff.domainchoreograph.core.api.DomainChoreographyMeta
import de.maibornwolff.domainchoreograph.core.api.DomainChoreographyOptions
import de.maibornwolff.domainchoreograph.core.processing.context.MutableDomainChoreographyStaticContext

abstract class DomainChoreographyBase(
    private val choreographyInterface: Class<*>,
    protected val options: DomainChoreographyOptions? = null
) {
    abstract val meta: DomainChoreographyMeta

    protected fun <T> run(methodName: String, choreography: (context: MutableDomainChoreographyStaticContext) -> T): T {
        return DomainChoreographyRunner(
            meta = meta,
            choreographyInterface = choreographyInterface,
            options = options
        ).run(methodName, choreography)
    }
}
