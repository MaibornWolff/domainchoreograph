package de.maibornwolff.domainchoreograph.core.processing.reflection

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.type.TypeMirror

private class MyAnnotation

class GetAnnotationTypeValueKtTest {
  @Test
  fun getAnnotationTypeValue() {
      val expectedTypeMirror = mock<TypeMirror>()

      val annotationValue = mock<AnnotationValue>()
      whenever(annotationValue.value).thenReturn(expectedTypeMirror)

      val valueKey = mock<ExecutableElement>()
      whenever(valueKey.simpleName).thenReturn(mock())
      whenever(valueKey.simpleName.toString()).thenReturn("value")

      val annotationMirror = mock<AnnotationMirror>()
      whenever(annotationMirror.annotationType).thenReturn(mock())
      whenever(annotationMirror.annotationType.toString()).thenReturn(MyAnnotation::class.java.canonicalName)
      whenever(annotationMirror.elementValues).thenReturn(mapOf(valueKey to annotationValue))

      val element = mock<Element>()
      whenever(element.annotationMirrors).thenReturn(listOf(annotationMirror))

      assertThat(element.getAnnotationTypeValue(MyAnnotation::class.java)).isEqualTo(expectedTypeMirror)
  }
}
