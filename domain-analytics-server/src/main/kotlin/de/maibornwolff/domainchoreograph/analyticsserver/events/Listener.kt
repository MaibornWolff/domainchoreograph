package de.maibornwolff.domainchoreograph.analyticsserver.events

typealias EventListenerCallback<T> = (value: T) -> Unit

class Listener<in T>(
    val callback: EventListenerCallback<T>,
    private val onUnsubscribe: Listener<T>.() -> Unit
) : Subscription {
  override fun unsubscribe() = onUnsubscribe()
}
