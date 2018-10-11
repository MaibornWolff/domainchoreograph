package de.maibornwolff.domainchoreography.exportdefinitions.utils

internal fun String.firstLetterLowercase() = if (this.isEmpty()) {
    ""
} else {
    this[0].toLowerCase().toString() + this.substring(1)
}
