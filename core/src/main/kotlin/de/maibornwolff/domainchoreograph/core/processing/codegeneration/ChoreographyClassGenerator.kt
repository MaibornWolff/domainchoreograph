package de.maibornwolff.domainchoreograph.core.processing.codegeneration

import com.squareup.kotlinpoet.*
import de.maibornwolff.domainchoreograph.core.processing.Method
import de.maibornwolff.domainchoreograph.core.processing.DomainChoreographyBase
import de.maibornwolff.domainchoreograph.core.api.DomainChoreographyMeta
import de.maibornwolff.domainchoreograph.core.api.DomainChoreographyOptions
import de.maibornwolff.domainchoreograph.core.processing.utils.NameUtils
import de.maibornwolff.domainchoreograph.core.processing.utils.getSimpleTypeName

internal class ChoreographyClassGenerator(
    private val choreographyTypeName: TypeName,
    private val choreographyMethods: List<Method>
) {
    private val className = NameUtils.getChoreographyImplementationName(choreographyTypeName.getSimpleTypeName())

    fun generate(): TypeSpec {
        val typeSpecBuilder = TypeSpec.classBuilder(className);

        addConstructor(typeSpecBuilder)
        addMetaProperty(typeSpecBuilder)

        choreographyMethods
            .forEach { method ->
                addChoreographyMethod(typeSpecBuilder, method)
            }

        return typeSpecBuilder.build()
    }

    private fun addConstructor(builder: TypeSpec.Builder) {
        builder
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter(
                        ParameterSpec
                            .builder("options", DomainChoreographyOptions::class.java.asTypeName().copy(nullable = true))
                            .defaultValue("null")
                            .build()
                    )
                    .build()
            )
            .addSuperinterface(choreographyTypeName)
            .superclass(DomainChoreographyBase::class)
            .addSuperclassConstructorParameter("%T::class.java", choreographyTypeName)
            .addSuperclassConstructorParameter("options")
    }

    private fun addMetaProperty(builder: TypeSpec.Builder) {
        builder
            .addProperty(
                PropertySpec.builder("meta", DomainChoreographyMeta::class, KModifier.OVERRIDE)
                    .initializer(NameUtils.getChoreographyMetaObjectName(choreographyTypeName.getSimpleTypeName()))
                    .build()
            )
    }

    private fun addChoreographyMethod(builder: TypeSpec.Builder, method: Method) {
        builder.addFunction(ChoreographyFunctionGenerator(method).generate())
    }

}
