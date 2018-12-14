package de.maibornwolff.domainchoreograph.exportdefinitions.model

import com.fasterxml.jackson.annotation.JsonProperty
import de.maibornwolff.domainchoreograph.exportdefinitions.utils.IdGenerator

data class ExportNode(
    val name: String,
    val scope: String? = null,
    val javaClass: String,
    val value: Any?,
    val exception: ExportException? = null,
    val hasException: Boolean = exception != null,
    val doc: String? = null,
    @Transient
    val type: Class<*>? = null,
    @get:JsonProperty("isService")
    val isService: Boolean = false,
    val id: String = "Node-" + generateId(),
    val preview: String = value?.toString() ?: ""
) {
  companion object : IdGenerator()
}
