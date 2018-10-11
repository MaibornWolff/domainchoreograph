package de.maibornwolff.domainchoreograph.analyticsserver.events

interface Subscribable<out T> {
  /** Subscribe to the events */
  fun subscribe(callback: EventListenerCallback<T>): Subscription
}
