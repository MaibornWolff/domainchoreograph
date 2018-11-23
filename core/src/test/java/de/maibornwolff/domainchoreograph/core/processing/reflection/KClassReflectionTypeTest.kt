package de.maibornwolff.domainchoreograph.core.processing.reflection

import com.squareup.kotlinpoet.asClassName
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredFunctions

interface MyInterface

open class MySuperClass {
    fun mySuperFunction() = 0
}

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE, AnnotationTarget.FUNCTION)
annotation class MyTestAnnotation(val value: KClass<*>)

@MyTestAnnotation(value = MyClass::class)
private class MyClass : MySuperClass(), MyInterface {
    @MyTestAnnotation(value = MyClass::class)
    fun myFunction(a: String) = "Test"

    companion object
}

private class MySimpleClass

class KClassAsReflectionTypeTest {
    @Test
    fun `should return ReflectionType`() {
        val reflectionType = MyClass::class.asReflectionType()

        assertThat(reflectionType).isInstanceOf(ReflectionType::class.java)
    }
}

class KTypeAsReflectionTypeTest {
    @Test
    fun `should return ReflectionType`() {
        val reflectionType = MyClass::class.declaredFunctions
            .toList()[0]
            .returnType
            .asReflectionType()

        assertThat(reflectionType).isInstanceOf(ReflectionType::class.java)
        assertThat(reflectionType.className.simpleName).isEqualTo("String")
    }
}

class KClassReflectionTypeTest {
    private lateinit var reflectionType: ReflectionType
    private lateinit var simpleReflectionType: ReflectionType

    @Before
    fun init() {
        reflectionType = MyClass::class.asReflectionType()
        simpleReflectionType = MySimpleClass::class.asReflectionType()
    }

    @Test
    fun getClassName() {
        assertThat(reflectionType.className).isEqualTo(MyClass::class.asClassName())
    }

    @Test
    fun getSuperclass() {
        assertThat(reflectionType.superclass?.className).isEqualTo(MySuperClass::class.asClassName())
    }

    @Test
    fun getInterfaces() {
        assertThat(reflectionType.interfaces.map { it.simpleName }).contains(MyInterface::class.asReflectionType().simpleName)
    }

    @Test
    fun getEnclosedMethods() {
        assertThat(reflectionType.enclosedMethods).contains(MyClass::class.declaredFunctions.toList()[0].asReflectionExecutable())
    }

    @Test
    fun getCompanionType() {
        assertThat(reflectionType.companionType).isEqualTo(MyClass.Companion::class.asReflectionType())
    }

    @Test
    fun `getAnnotation if not present`() {
        assertThat(simpleReflectionType.getAnnotation(MyTestAnnotation::class.java))
            .isNull()
    }

    @Test
    fun `getAnnotation if present`() {
        assertThat(reflectionType.getAnnotation(MyTestAnnotation::class.java))
            .isInstanceOf(MyTestAnnotation::class.java)
    }

    @Test
    fun getAnnotationTypeValue() {
        assertThat(reflectionType.getAnnotationTypeValue(MyTestAnnotation::class.java))
            .isEqualTo(MyClass::class.asReflectionType())
    }
}

class KReflectionExecutableTest {
    private lateinit var reflectionExecutable: ReflectionExecutable

    @Before
    fun init() {
        reflectionExecutable = MyClass::class.declaredFunctions.toList()[0].asReflectionExecutable()
    }

    @Test
    fun getName() {
        assertThat(reflectionExecutable.name).isEqualTo("myFunction")
    }

    @Test
    fun getParameters() {
        assertThat(reflectionExecutable.parameters.map { it.name }).contains("a")
    }

    @Test
    fun getReturnType() {
        assertThat(reflectionExecutable.returnType).isEqualTo(String::class.asReflectionType())
    }

    @Test
    fun getAnnotation() {
        assertThat(reflectionExecutable.getAnnotation(MyTestAnnotation::class.java))
            .isInstanceOf(MyTestAnnotation::class.java)
    }

    @Test
    fun getAnnotationTypeValue() {
        assertThat(reflectionExecutable.getAnnotationTypeValue(MyTestAnnotation::class.java))
            .isEqualTo(MyClass::class.asReflectionType())
    }
}
