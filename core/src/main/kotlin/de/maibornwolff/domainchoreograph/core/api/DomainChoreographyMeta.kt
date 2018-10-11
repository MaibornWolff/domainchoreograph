package de.maibornwolff.domainchoreograph.core.api

import de.maibornwolff.domainchoreograph.core.processing.dependencygraph.DependencyNode

data class DomainChoreographySchema(
    val rootNode: DependencyNode,
    val nodeOrder: List<DependencyNode>
)

interface DomainChoreographyMeta {
    val schemas: Map<String, DomainChoreographySchema>
}
