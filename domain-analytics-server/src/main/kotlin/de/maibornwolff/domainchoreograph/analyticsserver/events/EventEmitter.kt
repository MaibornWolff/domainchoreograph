package de.maibornwolff.domainchoreograph.analyticsserver.events

class EventEmitter<T> : Subscribable<T> {
  private val listeners: MutableSet<Listener<T>> = mutableSetOf()

  override fun subscribe(callback: EventListenerCallback<T>): Subscription {
    val listener = Listener(
        callback,
        onUnsubscribe = { listeners.remove(this) }
    )
    listeners.add(listener)
    return listener
  }

  /** Emit an value to all listeners */
  fun emit(value: T) = listeners.forEach { it.callback(value) }
}
