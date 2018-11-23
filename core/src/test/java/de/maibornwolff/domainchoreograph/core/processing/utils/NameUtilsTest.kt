package de.maibornwolff.domainchoreograph.core.processing.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class NameUtilsTest {
    @Test
    fun `should get ChoreographyImplementation name`() {
        assertThat(NameUtils.getChoreographyImplementationName("MyInterface"))
            .isEqualTo("MyInterfaceDomainImplementation")
    }

    @Test
    fun `should get ChoreographyMetaObject name`() {
        assertThat(NameUtils.getChoreographyMetaObjectName("MyInterface"))
            .isEqualTo("MyInterfaceDomainMeta")
    }
}
