package de.maibornwolff.domainchoreograph.exportdefinitions.utils

internal class UniqueIdGenerator(private val addNumberIfZero: Boolean = false) {
    private val ids = mutableSetOf<String>()

    fun createUniqueId(base: String): String {
        var count = 0
        while (ids.contains(getId(base, count))) {
            count++
        }
        return getId(base, count)
            .also { ids.add(it) }
    }

    private fun getId(base: String, count: Int) =
        if (count == 0 && !addNumberIfZero) base
        else "$base$count"
}
