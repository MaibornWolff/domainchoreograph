package de.maibornwolff.domainchoreograph.core.processing.utils

fun String.pascalCaseToCamelCase(): String = this.applyToFirstLetter { it.toLowerCase() }

fun String.camelCaseCaseToPascalCase(): String = this.applyToFirstLetter { it.toUpperCase() }

private fun String.applyToFirstLetter(change: (c: Char) -> Char): String {
    if (this.isEmpty()) {
        return this
    }
    return change(this[0]) + this.substring(1..(this.length - 1))
}
