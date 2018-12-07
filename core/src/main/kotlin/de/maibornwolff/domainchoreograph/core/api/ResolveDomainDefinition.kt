package de.maibornwolff.domainchoreograph.core.api

inline fun <reified T : Any> resolveDomainDefinition(vararg params: Any): T {
    return de.maibornwolff.domainchoreograph.core.processing.resolveDomainDefinition(T::class, params.asList())
}

inline fun <reified T : Any> resolveDomainDefinitionWithOptions(options: DomainChoreographyOptions, vararg params: Any): T {
    return de.maibornwolff.domainchoreograph.core.processing.resolveDomainDefinitionWithOptions(options, T::class, params.asList())
}
