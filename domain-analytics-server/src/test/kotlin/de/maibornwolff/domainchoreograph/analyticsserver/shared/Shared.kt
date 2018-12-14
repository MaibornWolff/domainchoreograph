package de.maibornwolff.domainchoreograph.analyticsserver.shared

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction

@DomainDefinition
class MyDomainDefinition

class NoDomainDefinition


@DomainDefinition
class Numbers(val values: List<Int>)

@DomainDefinition
class Sum {
    companion object {
      @DomainFunction
      fun calculate(numbers: Numbers) = numbers.values.sum()
    }
}
