package de.maibornwolff.domainchoreograph.core.api

import kotlin.reflect.KClass

@Target(AnnotationTarget.VALUE_PARAMETER)
internal annotation class DefinedBy(val value: KClass<*>)
