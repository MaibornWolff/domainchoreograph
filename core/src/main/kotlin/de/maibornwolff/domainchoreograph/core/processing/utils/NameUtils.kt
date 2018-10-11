package de.maibornwolff.domainchoreograph.core.processing.utils

object NameUtils {
    fun getChoreographyImplementationName(interfaceName: String) = "${interfaceName}DomainImplementation"

    fun getChoreographyMetaObjectName(interfaceName: String) = "${interfaceName}DomainMeta"
}
