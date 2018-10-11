package de.maibornwolff.domainchoreograph.core.processing.codegeneration

import com.squareup.kotlinpoet.CodeBlock
import de.maibornwolff.domainchoreograph.core.processing.dependencygraph.DependencyNode
import de.maibornwolff.domainchoreograph.core.processing.dependencygraph.DependencyGraph
import de.maibornwolff.domainchoreograph.core.processing.utils.getSimpleTypeName
import de.maibornwolff.domainchoreograph.core.processing.utils.pascalCaseToCamelCase

internal class ChoreographySchemaGenerator(
    private val dependencyGraph: DependencyGraph
) {
    fun generate(): CodeBlock {
        val builder = CodeBlock.builder()
        dependencyGraph.nodes.forEach { node -> addNode(builder, node) }
        addReturnStatement(builder)
        return builder.build()
    }

    private fun addNode(builder: CodeBlock.Builder, node: DependencyNode) {
        builder.add("val ${node.getSchemaVariableName()} = ")
        when (node) {
            is DependencyNode.VariableNode -> addVariableNode(builder, node)
            is DependencyNode.FunctionNode -> addFunctionNode(builder, node)
            is DependencyNode.ChoreographyNode -> addChoreographyNode(builder, node)
        }
        builder.add("\n")
    }

    private fun addReturnStatement(builder: CodeBlock.Builder) {
        builder.addStatement("""
        return DomainChoreographySchema(
            rootNode = ${dependencyGraph.target.getSchemaVariableName()},
            nodeOrder = listOf(${dependencyGraph.nodes
            .joinToString(", ") {
                it.getSchemaVariableName()
            }})
        )
        """.trimIndent())
    }

    private fun addVariableNode(builder: CodeBlock.Builder, node: DependencyNode.VariableNode) {
        builder
            .add("%T(\n", DependencyNode.VariableNode::class.java)
            .indent()
            .add("type = %T::class.java.asClassName(),\n", node.type)
            .add("domainType = %T::class.java.asClassName(),\n", node.domainType)
            .add("name = \"%L\"", node.name)
            .unindent()
            .add("\n)")
    }

    private fun addFunctionNode(builder: CodeBlock.Builder, node: DependencyNode.FunctionNode) {
        builder
            .add("%T(\n", DependencyNode.FunctionNode::class.java)
            .indent()
            .add("type = %T::class.java.asClassName(),\n", node.type)
            .add("domainType = %T::class.java.asClassName(),\n", node.domainType)
            .add("caller = %T::class.java.asClassName(),\n", node.caller)
            .add("name = \"%L\",\n", node.name)
            .add("parameters = %L", CodeBlock.builder()
                .add("listOf(")
                .indent()
                .apply {
                    node.parameters.forEachIndexed { index, parameter ->
                        this.add("\n")
                        this.add(parameter.getSchemaVariableName())
                        if (index != node.parameters.size - 1) {
                            this.add(",")
                        }
                    }
                }
                .unindent()
                .add(")")
                .build())
            .add("\n)")
            .unindent()
    }

    private fun addChoreographyNode(builder: CodeBlock.Builder, node: DependencyNode.ChoreographyNode) {
        builder
            .add("%T(\n", DependencyNode.ChoreographyNode::class.java)
            .add("type = %T::class.java.asClassName(),\n", node.type)
            .add("domainType = %T::class.java.asClassName(),\n", node.domainType)
            .add("caller = %T::class.java.asClassName()", node.caller)
            .add("\n)")
    }
}

private fun DependencyNode.getSchemaVariableName(): String = "${type.getSimpleTypeName().pascalCaseToCamelCase()}Schema";
