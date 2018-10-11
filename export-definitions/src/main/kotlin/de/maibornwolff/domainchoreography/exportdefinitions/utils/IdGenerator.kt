package de.maibornwolff.domainchoreograph.exportDefinitions.model.utils

open class IdGenerator {
    private var id = 0

    fun generateId(): Int {
        return id++
    }
}
