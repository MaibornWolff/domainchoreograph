package de.maibornwolff.domainchoreograph.core.processing.context

import de.maibornwolff.domainchoreograph.core.api.DomainContext

class MutableDomainChoreographyStaticContextStub : MutableDomainChoreographyStaticContext {
    override fun saveCalls(caller: Class<*>, choreographyClazz: Class<*>, calls: MutableList<DomainContext>) {
    }

    override fun save(caller: Class<*>, value: Any) {
    }

    override fun registerException(exception: Exception) {
    }
}
