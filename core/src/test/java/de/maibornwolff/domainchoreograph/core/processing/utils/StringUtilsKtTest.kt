package de.maibornwolff.domainchoreograph.core.processing.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class StringUtilsKtTest {

  @Test
  fun pascalCaseToCamelCase() {
      assertThat("HelloWorld".pascalCaseToCamelCase()).isEqualTo("helloWorld")
  }

  @Test
  fun camelCaseCaseToPascalCase() {
      assertThat("helloWorld".camelCaseCaseToPascalCase()).isEqualTo("HelloWorld")
  }
}
