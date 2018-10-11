package de.maibornwolff.domainchoreograph.core.api

interface DomainLogger {
  fun onComplete(context: DomainContext)
}
