package de.maibornwolff.domainchoreograph.analyticsserver.testingtool

import de.maibornwolff.domainchoreograph.core.api.DomainContext
import de.maibornwolff.domainchoreograph.core.api.DomainLogger

class ContextLogger : DomainLogger {
    var context: DomainContext? = null

    override fun onComplete(context: DomainContext) {
        this.context = context
    }
}
