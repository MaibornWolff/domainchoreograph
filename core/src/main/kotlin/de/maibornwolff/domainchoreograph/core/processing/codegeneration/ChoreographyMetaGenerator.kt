package de.maibornwolff.domainchoreograph.core.processing.codegeneration

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import de.maibornwolff.domainchoreograph.core.processing.Method
import de.maibornwolff.domainchoreograph.core.api.DomainChoreographyMeta
import de.maibornwolff.domainchoreograph.core.api.DomainChoreographySchema
import de.maibornwolff.domainchoreograph.core.processing.utils.NameUtils
import de.maibornwolff.domainchoreograph.core.processing.utils.camelCaseCaseToPascalCase
import de.maibornwolff.domainchoreograph.core.processing.utils.getSimpleTypeName

internal class ChoreographyMetaGenerator(
    choreographyTypeName: TypeName,
    private val choreographyMethods: List<Method>
) {
    private val className = NameUtils.getChoreographyMetaObjectName(choreographyTypeName.getSimpleTypeName())

    fun generate(): TypeSpec {
        val typeSpecBuilder = TypeSpec
            .objectBuilder(className)
            .addSuperinterface(DomainChoreographyMeta::class)
            .addProperty(
                PropertySpec.builder(
                    "schemas",
                    Map::class
                        .asClassName()
                        .parameterizedBy(String::class.asTypeName(), DomainChoreographySchema::class.asTypeName()),
                    KModifier.OVERRIDE
                )
                    .initializer(createSchemasInitializer())
                    .build()
            )

        choreographyMethods
            .forEach { method ->
                typeSpecBuilder.addFunction(
                    FunSpec.builder(getSchemaGetterName(method.name))
                        .returns(DomainChoreographySchema::class)
                        .addCode(ChoreographySchemaGenerator(method.dependencyGraph).generate())
                        .build()
                )
            }

        return typeSpecBuilder.build()
    }

    private fun createSchemasInitializer(): CodeBlock {
        val builder = CodeBlock.builder()
        builder.add("mapOf(\n")
        builder.indent()
        choreographyMethods.forEach { method ->
            builder.add("\"${method.name}\" to ${getSchemaGetterName(method.name)}()")
        }
        builder.unindent()
        builder.add("\n)")
        return builder.build()
    }

    private fun getSchemaGetterName(methodName: String) = "get${methodName.camelCaseCaseToPascalCase()}Schema"
}
