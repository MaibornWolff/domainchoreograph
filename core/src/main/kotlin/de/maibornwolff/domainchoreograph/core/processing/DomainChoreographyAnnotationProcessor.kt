package de.maibornwolff.domainchoreograph.core.processing

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.asTypeName
import de.maibornwolff.domainchoreograph.core.api.DomainChoreography
import de.maibornwolff.domainchoreograph.core.processing.codegeneration.ChoreographyGenerator
import de.maibornwolff.domainchoreograph.core.processing.dependencygraph.DependencyGraph
import de.maibornwolff.domainchoreograph.core.processing.reflection.asReflectionExecutable
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.ElementFilter

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("de.maibornwolff.domainchoreograph.core.api.DomainChoreography")
@SupportedOptions(DomainChoreographyAnnotationProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class DomainChoreographyAnnotationProcessor : AbstractProcessor() {
    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    override fun process(set: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        val elements = roundEnv.getElementsAnnotatedWith(DomainChoreography::class.java)
        if (elements.isEmpty()) {
            return false
        }

        elements.forEach { generateChoreographyCode(it as TypeElement) }
        return true
    }

    private fun generateChoreographyCode(choreographyClass: TypeElement) {
        val javaPackage = processingEnv.elementUtils.getPackageOf(choreographyClass).toString()

        val files = ChoreographyGenerator(
            javaPackage,
            choreographyClass.asType().asTypeName(),
            createChoreographyMethods(choreographyClass)
        ).generate()

        files.forEach { file -> saveFile(file) }
    }

    private fun createChoreographyMethods(choreographyClass: TypeElement): List<Method> {
        return ElementFilter.methodsIn(choreographyClass.enclosedElements)
            .map { method ->
                val reflectionExecutable = method.asReflectionExecutable(processingEnv)
                Method(
                    name = method.simpleName.toString(),
                    parameters = method.parameters.map {
                        Parameter(name = it.simpleName.toString(), typeName = it.asType().asTypeName())
                    },
                    dependencyGraph = DependencyGraph.create(
                        reflectionExecutable.returnType,
                        reflectionExecutable.parameters
                    ),
                    returnType = method.returnType.asTypeName()
                )
            }
    }

    private fun saveFile(file: FileSpec) {
        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        file.writeTo(File(kaptKotlinGeneratedDir))
    }
}
