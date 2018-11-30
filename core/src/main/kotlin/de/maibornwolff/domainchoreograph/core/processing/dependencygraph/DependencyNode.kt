package de.maibornwolff.domainchoreograph.core.processing.dependencygraph

import com.squareup.kotlinpoet.ClassName
import de.maibornwolff.domainchoreograph.core.processing.utils.getSimpleTypeName
import de.maibornwolff.domainchoreograph.core.processing.utils.pascalCaseToCamelCase

sealed class DependencyNode(open val type: ClassName, open val domainType: ClassName) {

    data class FunctionNode(
        override val type: ClassName,
        override val domainType: ClassName,
        val caller: ClassName,
        val name: String,
        val parameters: List<DependencyNode> = listOf()
    ) : DependencyNode(type, domainType) {
        fun getVariableName(): String = domainType.getSimpleTypeName().pascalCaseToCamelCase()
    }

    data class VariableNode(
        override val type: ClassName,
        override val domainType: ClassName,
        val name: String
    ) : DependencyNode(type, domainType)

    data class ChoreographyNode(
        override val type: ClassName,
        override val domainType: ClassName,
        val caller: ClassName
    ) : DependencyNode(type, domainType) {
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
