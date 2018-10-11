package de.maibornwolff.domainchoreography.exportdefinitions.model

data class ExportException(
  val detailMessage: String,
  val stackTrace: List<String> = listOf(),
  val suppressedExceptions: List<String> = listOf()
)

fun Throwable.toExportException() = ExportException(
    detailMessage = message ?: "",
    stackTrace = stackTrace.map { it.toString() },
    suppressedExceptions = suppressed.map { it.toString() }
)
