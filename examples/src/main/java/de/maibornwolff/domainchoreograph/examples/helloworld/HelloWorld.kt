package de.maibornwolff.domainchoreograph.examples.helloworld

import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainFunction

@DomainDefinition
data class HelloWorld(val message: String) {

    companion object {

        @DomainFunction
        fun resolveHelloWorld(name: Name): HelloWorld {
            return HelloWorld("Hello " + name.value)
        }
    }
}
