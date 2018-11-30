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
import kotlin.reflect.full.valueParameters

fun KClass<*>.asReflectionType(): ReflectionType = KClassReflectionType(this)

fun KType.asReflectionType(): ReflectionType = KClassReflectionType(this.classifier as KClass<*>)

private data class KClassReflectionType(
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
}

fun KFunction<*>.asReflectionExecutable(): ReflectionExecutable {
    return KFunctionReflectionExecutable(this)
}

private data class KFunctionReflectionExecutable(
    private val base: KFunction<*>
) : ReflectionExecutable {
    override val name: String
        get() = base.name

    override val parameters: List<ReflectionVariable>
        get() = base.valueParameters
            .map { it.asReflectionVariable() }

    override val returnType: ReflectionType
        get() = base.returnType.asReflectionType()

    override fun <T : Annotation> getAnnotation(annotation: Class<T>): T? {
        return base.annotations
            .find { annotation.isInstance(it) } as T?
    }
}

fun KParameter.asReflectionVariable(): ReflectionVariable = KParameterReflectionVariable(this)

private data class KParameterReflectionVariable(
    private val base: KParameter
) : ReflectionVariable {
    override val name: String
        get() = base.name!!

    override val type: ReflectionType
        get() = base.type.asReflectionType()

    override fun <T : Annotation> getAnnotation(annotation: Class<T>): T? {
        return base.annotations.find { annotation.isInstance(it) } as T?
    }
}

data class KReflectionVariable(
    override val name: String,
    override val type: ReflectionType
) : ReflectionVariable {
    override fun <T : Annotation> getAnnotation(annotation: Class<T>): T? = null
}

