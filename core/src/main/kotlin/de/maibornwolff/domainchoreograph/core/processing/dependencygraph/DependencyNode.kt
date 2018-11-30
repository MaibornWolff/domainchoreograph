package de.maibornwolff.domainchoreograph.core.processing.dependencygraph

import com.squareup.kotlinpoet.ClassName
import de.maibornwolff.domainchoreograph.core.processing.utils.getSimpleTypeName
import de.maibornwolff.domainchoreograph.core.processing.utils.pascalCaseToCamelCase

sealed class DependencyNode {
    abstract val type: ClassName
    abstract val domainType: ClassName

    data class FunctionNode(
        override val type: ClassName,
        override val domainType: ClassName,
        val caller: ClassName,
        val name: String,
        val parameters: List<DependencyNode> = listOf()
    ) : DependencyNode() {
        fun getVariableName(): String = domainType.getSimpleTypeName().pascalCaseToCamelCase()
    }

    data class VariableNode(
        override val type: ClassName,
        override val domainType: ClassName,
        val name: String
    ) : DependencyNode()

    data class ChoreographyNode(
        override val type: ClassName,
        override val domainType: ClassName,
        val caller: ClassName
    ) : DependencyNode() {
        fun getVariableName(): String {
            val typeName = domainType.getSimpleTypeName()
            val callerTypeName = caller.getSimpleTypeName()
            return "${callerTypeName.pascalCaseToCamelCase()}$typeName"
        }

        fun getCallsVariableName(): String =
            "${getVariableName()}Calls"

    }

    override fun toString(): String {
        return type.toString()
    }
}
