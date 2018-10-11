package de.maibornwolff.domainchoreograph.core.processing.reflection

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asClassName
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.KType
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.superclasses

fun KClass<*>.asReflectionType(): ReflectionType = KClassReflectionType(this)

fun KType.asReflectionType(): ReflectionType = KClassReflectionType(this as KClass<*>)

private class KClassReflectionType(
    private val base: KClass<*>
) : ReflectionType {
    override val className: ClassName
        get() = base.asClassName()

    override val superclass: ReflectionType?
        get() = base.superclasses
            .asSequence()
            .filter { !it.java.isInterface }
            .firstOrNull()
            ?.asReflectionType()

    override val interfaces: List<ReflectionType>
        get() = base.superclasses
            .asSequence()
            .filter { it.java.isInterface }
            .map { it.asReflectionType() }
            .toList()

    override val enclosedMethods: List<ReflectionExecutable>
        get() = base.declaredFunctions
            .map { it.asReflectionExecutable() }

    override val companionType: ReflectionType?
        get() = base.companionObject?.asReflectionType()

    override fun <T : Annotation> getAnnotation(annotation: Class<T>): T? {
        return base.java.getAnnotation(annotation)
    }

    override fun <T : Annotation> getAnnotationTypeValue(annotation: Class<T>): ReflectionType? =
        base.java.getAnnotationTypeValueAsReflectionType(annotation)

}

fun KFunction<*>.asReflectionExecutable(): ReflectionExecutable {
    return KFunctionReflectionExecutable(this)
}

private class KFunctionReflectionExecutable(
    private val base: KFunction<*>
) : ReflectionExecutable {
    override val name: String
        get() = base.name
    override val parameters: List<ReflectionVariable>
        get() = base.parameters
            .map { it.asReflectionVariable() }
    override val returnType: ReflectionType
        get() = base.returnType.asReflectionType()

    override fun <T : Annotation> getAnnotation(annotation: Class<T>): T? =
        base.javaClass.getAnnotation(annotation)

    override fun <T : Annotation> getAnnotationTypeValue(annotation: Class<T>): ReflectionType? =
        base.javaClass.getAnnotationTypeValueAsReflectionType(annotation)
}

fun KParameter.asReflectionVariable(): ReflectionVariable = KParameterReflectionVariable(this)

private class KParameterReflectionVariable(
    private val base: KParameter
) : ReflectionVariable {
    override val name: String
        get() = base.name!!

    override val type: ReflectionType
        get() = base.type.asReflectionType()

    override fun <T : Annotation> getAnnotation(annotation: Class<T>): T? {
        return base.javaClass.getAnnotation(annotation)
    }

    override fun <T : Annotation> getAnnotationTypeValue(annotation: Class<T>): ReflectionType? =
        base.javaClass.getAnnotationTypeValueAsReflectionType(annotation)
}

private fun <T : Annotation> Class<*>.getAnnotationTypeValueAsReflectionType(annotation: Class<T>): ReflectionType {
    val annotation = getAnnotation(annotation)
    val nameField = annotation.javaClass.getField("name")
    return (nameField.get(annotation) as Class<*>).kotlin.asReflectionType()
}
