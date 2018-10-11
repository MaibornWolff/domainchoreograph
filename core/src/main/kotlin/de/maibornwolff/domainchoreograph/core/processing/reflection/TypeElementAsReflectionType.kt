package de.maibornwolff.domainchoreograph.core.processing.reflection

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asClassName
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.ElementFilter

fun TypeElement.asReflectionType(env: ProcessingEnvironment): ReflectionType = TypeElementReflectionType(env, this)

private class TypeElementReflectionType(
    private val env: ProcessingEnvironment,
    private val element: TypeElement
) : ReflectionType {
    override val className: ClassName
        get() = element.asClassName()

    override val superclass: ReflectionType?
        get() = element.superclass.asReflectionType(env)

    override val interfaces: List<ReflectionType>
        get() = element.interfaces
            .asSequence()
            .map { it.asReflectionType(env) }
            .filterNotNull()
            .toList()

    override val enclosedMethods: List<ReflectionExecutable>
        get() = ElementFilter.methodsIn(element.enclosedElements)
            .map { it.asReflectionExecutable(env) }

    override val companionType: ReflectionType?
        get() = ElementFilter.typesIn(element.enclosedElements)
            .find { it.simpleName.toString() == "Companion" }
            ?.asReflectionType(env)

    override fun <T : Annotation> getAnnotation(annotation: Class<T>): T? {
        return element.getAnnotation(annotation)
    }

    override fun <T : Annotation> getAnnotationTypeValue(annotation: Class<T>): ReflectionType? =
        element.getAnnotationTypeValueAsReflectionType(env, annotation)
}

fun ExecutableElement.asReflectionExecutable(env: ProcessingEnvironment): ReflectionExecutable =
    ExecutableElementReflectionExecutable(env, this)

class ExecutableElementReflectionExecutable(
    private val env: ProcessingEnvironment,
    private val element: ExecutableElement
) : ReflectionExecutable {

    override val name: String
        get() = element.simpleName.toString()

    override val parameters: List<ReflectionVariable>
        get() = element.parameters
            .map { it.asReflectionVariable(env) }

    override val returnType: ReflectionType
        get() = element.returnType.asReflectionType(env)!!

    override fun <T : Annotation> getAnnotation(annotation: Class<T>): T? {
        return element.getAnnotation(annotation)
    }

    override fun <T : Annotation> getAnnotationTypeValue(annotation: Class<T>): ReflectionType? =
        element.getAnnotationTypeValueAsReflectionType(env, annotation)

}

fun VariableElement.asReflectionVariable(env: ProcessingEnvironment): ReflectionVariable =
    VariableElementReflectionVariable(env, this)

class VariableElementReflectionVariable(
    private val env: ProcessingEnvironment,
    private val element: VariableElement
) : ReflectionVariable {
    override val name: String
        get() = element.simpleName.toString()

    override val type: ReflectionType
        get() = element.asType().asReflectionType(env)!!

    override fun <T : Annotation> getAnnotation(annotation: Class<T>): T? {
        return element.getAnnotation(annotation)
    }

    override fun <T : Annotation> getAnnotationTypeValue(annotation: Class<T>): ReflectionType? =
        element.getAnnotationTypeValueAsReflectionType(env, annotation)
}

fun TypeMirror.asReflectionType(env: ProcessingEnvironment): ReflectionType? =
    asTypeElement()?.asReflectionType(env)

private fun TypeMirror.asTypeElement(): TypeElement? {
    if (this.kind != TypeKind.DECLARED) {
        return null
    }
    return (this as DeclaredType).asElement() as TypeElement
}

private fun Element.getAnnotationTypeValueAsReflectionType(env: ProcessingEnvironment, annotation: Class<*>) =
    getAnnotationTypeValue(annotation)?.asReflectionType(env)

