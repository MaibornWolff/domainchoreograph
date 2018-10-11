package de.maibornwolff.domainchoreograph.core.processing.codegeneration

import com.squareup.kotlinpoet.*
import de.maibornwolff.domainchoreograph.core.processing.Method
import de.maibornwolff.domainchoreograph.core.processing.dependencygraph.DependencyNode
import de.maibornwolff.domainchoreograph.core.processing.dependencygraph.DependencyGraph
import de.maibornwolff.domainchoreograph.core.api.DomainContext
import de.maibornwolff.domainchoreograph.core.api.DomainChoreographyOptions
import de.maibornwolff.domainchoreograph.core.api.DomainEnvironment
import de.maibornwolff.domainchoreograph.core.processing.utils.getSimpleTypeName
import de.maibornwolff.domainchoreograph.core.processing.utils.pascalCaseToCamelCase

internal class ChoreographyFunctionGenerator(
    private val method: Method
) {
    fun generate(): FunSpec {
        return FunSpec.builder(method.name)
            .addModifiers(KModifier.OVERRIDE)
            .addParameters(method.parameters.map {
                ParameterSpec.builder(it.name, it.typeName).build()
            })
            .returns(method.returnType)
            .addCode(createBody(method.name, method.dependencyGraph))
            .build()
    }

    private fun createBody(name: String, graph: DependencyGraph): CodeBlock {
        val builder = CodeBlock.builder()
        val variableMapping = mutableMapOf<TypeName, String>()

        builder.add("return run(\"$name\") { context -> \n")
        builder.indent()
        graph.nodes
            .forEachIndexed { i, node ->
                if (variableMapping.containsKey(node.type)) {
                    return@forEachIndexed
                }
                when (node) {
                    is DependencyNode.VariableNode -> {
                        addVariableNode(builder, variableMapping, node)
                    }
                    is DependencyNode.FunctionNode -> {
                        addFunctionNode(builder, variableMapping, node)
                        val isLast = i == graph.nodes.size - 1
                        if (isLast) {
                            builder.newLine()
                            builder.add("return@run ${node.getVariableName()}\n")
                        }
                    }
                    is DependencyNode.ChoreographyNode -> {
                        addChoreographyNode(builder, variableMapping, node)
                    }
                }

            }
        builder.unindent()
        builder.add("}\n")
        return builder.build()
    }

    private fun addVariableNode(
        builder: CodeBlock.Builder,
        variableMapping: MutableMap<TypeName, String>,
        node: DependencyNode.VariableNode
    ) {
        variableMapping[node.type] = node.name
        builder.add("context.save(%T::class.java, %N)\n", node.domainType, node.name)
    }

    private fun addFunctionNode(
        builder: CodeBlock.Builder,
        variableMapping: MutableMap<TypeName, String>,
        node: DependencyNode.FunctionNode
    ) {
        val typeName = node.type.getSimpleTypeName()
        val name = typeName.pascalCaseToCamelCase()
        val parameters = node.parameters.joinToString(", ") { variableMapping[it.type]!! }
        builder.add("\n")
        builder.add("val $name =")
        builder.add(" %T.${node.name}($parameters)\n", node.caller)
        builder.add("context.save(%T::class.java, %N)\n", node.domainType, name)

        variableMapping[node.type] = name
    }

    private fun addChoreographyNode(
        builder: CodeBlock.Builder,
        variableMapping: MutableMap<TypeName, String>,
        node: DependencyNode.ChoreographyNode
    ) {
        val name = node.getVariableName()
        val callName = node.getCallsVariableName()
        builder.add("\n")
        builder.add("val $callName = mutableListOf<%T>()\n", DomainContext::class.java)
        builder.add(
            "val $name = %T().get(\n",
            DomainEnvironment::class.java
        )
        builder.indent()
        builder.add(
            "%T::class.java,\n",
            node.type
        )
        builder.add(
            "%T(calls = $callName)\n",
            DomainChoreographyOptions::class.java
        )
        builder.unindent()
        builder.add(")\n")
        builder.add(
            "context.saveCalls(%T::class.java, %T::class.java, %N)\n",
            node.caller,
            node.domainType,
            node.getCallsVariableName()
        )
        variableMapping[node.type] = name
    }
}

private fun CodeBlock.Builder.newLine() {
    this.add("\n")
}
