package de.maibornwolff.domainchoreograph.analyticsserver.events

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*

internal class EventEmitterTest {

  @Test
  fun subscribeAndEmit() {
    val emitter = EventEmitter<String>()
    val expectedValue = "test"

    var actualValue: String? = null
    emitter.subscribe { actualValue = it }
    emitter.emit(expectedValue)

    assertEquals(expectedValue, actualValue)
  }

}
