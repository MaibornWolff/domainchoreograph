package de.maibornwolff.domainchoreograph.core.processing.context

import de.maibornwolff.domainchoreograph.core.api.DomainContext

interface CastableToDomainChoreographyStaticContext {
    fun toDomainChoreographyStaticContext(): DomainContext
}
