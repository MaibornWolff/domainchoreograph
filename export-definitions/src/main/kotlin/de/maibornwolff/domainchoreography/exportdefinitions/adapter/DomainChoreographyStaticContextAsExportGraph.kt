package de.maibornwolff.domainchoreography.exportdefinitions.adapter

import de.maibornwolff.domainchoreograph.core.processing.dependencygraph.DependencyNode
import de.maibornwolff.domainchoreograph.core.api.DomainContext
import de.maibornwolff.domainchoreograph.core.api.DomainContextNode
import de.maibornwolff.domainchoreograph.core.processing.utils.asJavaClass
import de.maibornwolff.domainchoreography.exportdefinitions.model.*
import de.maibornwolff.domainchoreography.exportdefinitions.utils.UniqueIdGenerator
import java.util.*

fun DomainContext.asExportGraph(id: String = UUID.randomUUID().toString()): ExportGraph {
    return ExportGraphCreator(id).create(this)
}

private class ExportGraphCreator(val id: String) {
    private val nodes = mutableMapOf<String, ExportNode>()
    private val dependencies = mutableListOf<ExportDependency>()
    private val scopes = mutableMapOf<String, ExportScope>()
    private val executionContexts = mutableMapOf<String, ExportExecutionContext>()

    private val dependencyNodeToExportNode = mutableMapOf<DependencyNode, ExportNode>()

    private val uniqueNodeIdGenerator = UniqueIdGenerator()
    private val uniqueScopeIdGenerator = UniqueIdGenerator(addNumberIfZero = true)

    fun create(context: DomainContext): ExportGraph {
        val rootContext = ExportExecutionContext(
            id = "Application",
            scopes = mutableListOf("root")
        )
        executionContexts[rootContext.id] = rootContext
        val rootScope = ExportScope(
            id = "root",
            executionContext = rootContext.id,
            nodes = mutableListOf()
        )
        scopes[rootScope.id] = rootScope

        setExportVariablesFromContext(context, rootScope)

        return ExportGraph(
            id = id,
            nodes = nodes,
            dependencies = dependencies,
            scopes = scopes,
            executionContexts = executionContexts
        )
    }

    private fun setExportVariablesFromContext(
        context: DomainContext,
        exportScope: ExportScope
    ) {
        context.schema.nodeOrder
            .forEach { node -> addExportVariablesFromDependencyNode(node, context, exportScope) }
    }

    private fun addExportVariablesFromDependencyNode(dependencyNode: DependencyNode, context: DomainContext, exportScope: ExportScope) {
        if (dependencyNode is DependencyNode.ChoreographyNode) return

        val (exportNode, contextNode) = createExportNode(dependencyNode, context, exportScope)
        addExportNode(exportNode, dependencyNode)

        val exportDependencies = createExportDependencies(dependencyNode, exportNode)
        addExportDependencies(exportDependencies)

        if (contextNode?.subContextMapping?.isNotEmpty() == true) {
            val exportContext = createExportContext(exportNode)
            addExportContext(exportContext)

            createScopes(exportContext, contextNode)
                .forEach { (subContext, scope) -> addScope(subContext, scope) }
        }
    }

    private fun createExportNode(node: DependencyNode, context: DomainContext, exportScope: ExportScope): Pair<ExportNode, DomainContextNode<*>?> {
        val domainTypeAsJavaClass = node.domainType.asJavaClass()
        val contextNode = context.nodes[domainTypeAsJavaClass]
        val exportNodeCreator = ExportNodeCreator(uniqueNodeIdGenerator, exportScope)

        val exportNode = exportNodeCreator.create(
            type = node.domainType.asJavaClass(),
            name = node.getExportName(),
            value = contextNode?.value,
            exception = contextNode?.exception
        )
        return Pair(exportNode, contextNode)
    }

    private fun addExportNode(exportNode: ExportNode, node: DependencyNode) {
        nodes[exportNode.id] = exportNode
        dependencyNodeToExportNode[node] = exportNode
    }

    private fun createExportDependencies(dependencyNode: DependencyNode, exportNode: ExportNode): List<ExportDependency> {
        if (dependencyNode !is DependencyNode.FunctionNode) {
            return listOf()
        }

        return dependencyNode.parameters
            .asSequence()
            .filter { it !is DependencyNode.ChoreographyNode }
            .map { param ->
                ExportDependency(
                    src = dependencyNodeToExportNode[param]!!.id,
                    target = exportNode.id
                )
            }
            .toList()
    }

    private fun addExportDependencies(exportDependencies: List<ExportDependency>) {
        dependencies.addAll(exportDependencies)
    }

    private fun createExportContext(exportNode: ExportNode): ExportExecutionContext =
        ExportExecutionContextCreator(exportNode).create()

    private fun addExportContext(context: ExportExecutionContext) {
        executionContexts[context.id] = context
    }

    private fun createScopes(subContext: ExportExecutionContext, contextNode: DomainContextNode<*>): List<Pair<DomainContext, ExportScope>> {
        val scopeCreator = ExportScopeCreator(uniqueScopeIdGenerator, subContext)
        return contextNode.subContextMapping.values
            .flatMap { it }
            .map { Pair(it, scopeCreator.create()) }
    }

    private fun addScope(context: DomainContext, scope: ExportScope) {
        scopes[scope.id] = scope
        setExportVariablesFromContext(context, scope)
    }
}

private fun DependencyNode.getExportName() = domainType.simpleName
