package de.maibornwolff.domainchoreograph.analyticsserver

import de.maibornwolff.domainchoreograph.analyticsserver.testingtool.JsonEncodedClass

class RunChoregraphyRequest(
    val targetCanonicalName: String,
    val inputs: Set<JsonEncodedClass>
)
