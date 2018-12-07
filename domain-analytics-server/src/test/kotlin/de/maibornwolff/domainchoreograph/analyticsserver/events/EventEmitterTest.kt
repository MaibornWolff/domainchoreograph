package de.maibornwolff.domainchoreograph.analyticsserver.events

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


internal class EventEmitterTest {

  @Test
  fun subscribeAndEmit() {
    val emitter = EventEmitter<String>()
    val expectedValue = "test"

    var actualValue: String? = null
    emitter.subscribe { actualValue = it }
    emitter.emit(expectedValue)

    assertThat(actualValue).isEqualTo(expectedValue)
  }

}
