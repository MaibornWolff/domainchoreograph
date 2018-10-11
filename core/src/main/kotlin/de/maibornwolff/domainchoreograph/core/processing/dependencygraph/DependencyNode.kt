package de.maibornwolff.domainchoreograph.core.processing.dependencygraph

import com.squareup.kotlinpoet.ClassName
import de.maibornwolff.domainchoreograph.core.processing.utils.getSimpleTypeName
import de.maibornwolff.domainchoreograph.core.processing.utils.pascalCaseToCamelCase

sealed class DependencyNode(val type: ClassName, val domainType: ClassName) {

    class FunctionNode(
        type: ClassName,
        domainType: ClassName,
        val caller: ClassName,
        val name: String,
        val parameters: List<DependencyNode> = listOf()
    ) : DependencyNode(type, domainType) {
        fun getVariableName(): String = domainType.getSimpleTypeName().pascalCaseToCamelCase()
    }

    class VariableNode(
        type: ClassName,
        domainType: ClassName,
        val name: String
    ) : DependencyNode(type, domainType)

    class ChoreographyNode(
        type: ClassName,
        domainType: ClassName,
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
