package de.maibornwolff.domainchoreograph.scenarios.choreographywithexception

import de.maibornwolff.domainchoreograph.core.api.DomainChoreography
import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction
import de.maibornwolff.domainchoreograph.scenarios.simplechoreography.SimpleDomainObject1
import de.maibornwolff.domainchoreograph.scenarios.simplechoreography.SimpleDomainObject2
import java.lang.RuntimeException

data class TestException(val msg: String = "TestException") : RuntimeException(msg)

@DomainDefinition
data class ExceptionDomainObject(val sum: Int) {
    companion object {
        @DomainFunction
        fun create(d1: SimpleDomainObject1, d2: SimpleDomainObject2): ExceptionDomainObject {
            throw TestException()
        }
    }
}

@DomainChoreography
interface ChoreographyWithException {
    fun calculate(d1: SimpleDomainObject1, d2: SimpleDomainObject2): ExceptionDomainObject
}
