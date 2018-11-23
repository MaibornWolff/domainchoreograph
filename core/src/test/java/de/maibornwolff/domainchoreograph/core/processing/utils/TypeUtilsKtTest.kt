package de.maibornwolff.domainchoreograph.core.processing.utils

import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

private class MyTestClass

class TypeUtilsKtTest {

  @Test
  fun getSimpleTypeName() {
      assertThat(MyTestClass::class.java.asTypeName().getSimpleTypeName())
          .isEqualTo("MyTestClass")
  }

  @Test
  fun asJavaClass() {
      assertThat(MyTestClass::class.java.asClassName().asJavaClass())
          .isEqualTo(MyTestClass::class.java)
  }
}
