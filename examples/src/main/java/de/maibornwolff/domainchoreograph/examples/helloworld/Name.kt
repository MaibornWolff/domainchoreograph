package de.maibornwolff.domainchoreograph.examples.helloworld

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition

@DomainDefinition
data class Name(val value: String)
