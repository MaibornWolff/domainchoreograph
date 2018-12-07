package de.maibornwolff.domainchoreograph.analyticsserver.events

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

internal class ListenerTest {

  @Test
  fun unsubscribe() {
    val unsubscribeCallback: Listener<String>.() -> Unit = mock()

    val listener: Listener<String> = Listener({}, unsubscribeCallback)
    listener.unsubscribe()

    verify(unsubscribeCallback).invoke(listener)
  }
}
