package de.maibornwolff.domainchoreograph.core.processing.reflection

import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.Element
import javax.lang.model.type.TypeMirror

private fun Element.getAnnotationMirror(clazz: Class<*>): AnnotationMirror? {
    val clazzName = clazz.name
    return annotationMirrors
        .find { it.annotationType.toString() == clazzName }
}

private fun AnnotationMirror.getAnnotationTypeParameter(key: String): AnnotationValue? {
    return elementValues
        .entries
        .find { it.key.simpleName.toString() == key }
        ?.value
}


fun Element.getAnnotationTypeValue(annotation: Class<*>): TypeMirror? {
    return getAnnotationMirror(annotation)
        ?.getAnnotationTypeParameter("value")
        ?.value
        ?.let { it as TypeMirror }
}
