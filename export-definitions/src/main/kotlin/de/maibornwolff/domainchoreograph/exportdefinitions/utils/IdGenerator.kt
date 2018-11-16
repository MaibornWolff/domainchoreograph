package de.maibornwolff.domainchoreograph.exportdefinitions.utils

open class IdGenerator {
    private var id = 0

    fun generateId(): Int {
        return id++
    }
}
