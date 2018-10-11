package de.maibornwolff.domainchoreograph.core.processing.utils

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName

fun TypeName.getSimpleTypeName(): String =
    this.toString().split(".").last()

fun ClassName.asJavaClass(): Class<*> =
    javaClass.classLoader.loadClass(this.reflectionName())
