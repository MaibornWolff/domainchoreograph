package de.maibornwolff.domainchoreograph.core.api


@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class DomainService(val value: String = "USE_CLASSNAME")
