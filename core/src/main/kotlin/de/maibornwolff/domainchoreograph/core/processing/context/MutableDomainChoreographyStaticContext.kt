package de.maibornwolff.domainchoreograph.core.processing.context

import de.maibornwolff.domainchoreograph.core.api.DomainContext

interface MutableDomainChoreographyStaticContext {
    fun save(caller: Class<*>, value: Any)
    fun saveCalls(caller: Class<*>, choreographyClazz: Class<*>, calls: MutableList<DomainContext>)
    fun registerException(exception: Exception)
}

