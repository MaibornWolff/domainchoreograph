package de.maibornwolff.domainchoreograph.core.processing.codegeneration

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeName
import de.maibornwolff.domainchoreograph.core.processing.Method

class ChoreographyGenerator(
    private val javaPackage: String,
    private val choreographyTypeName: TypeName,
    private val choreographyMethods: List<Method>
) {
    fun generate(): List<FileSpec> {
        return listOf(createClassFile(), createMetaFile())
    }

    private fun createClassFile(): FileSpec {
        val choreographyClass = ChoreographyClassGenerator(
            choreographyTypeName,
            choreographyMethods
        ).generate()
        return FileSpec.builder(javaPackage, choreographyClass.name!!)
            .addType(choreographyClass)
            .build()
    }

    private fun createMetaFile(): FileSpec {
        val choreographyMetaObject = ChoreographyMetaGenerator(
            choreographyTypeName,
            choreographyMethods
        ).generate()

        return FileSpec.builder(javaPackage, choreographyMetaObject.name!!)
            .addImport("com.squareup.kotlinpoet", "asClassName")
            .addType(choreographyMetaObject)
            .build()
    }
}
