package de.maibornwolff.domainchoreograph.analyticsserver.testingtool

import getJsonSchemaFromClass
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*

class MySubClass(
    val a: Int
)

class MyClass(
    val a: String,
    val b: Int,
    val c: Double,
    val d: Date,
    val e: MySubClass
)

class GetJsonSchemaFromClassTest {
    @Test
    fun `should work`() {
        val schema = getJsonSchemaFromClass(MyClass::class.java)

        assertThat(schema).isEqualTo("""
        {
          "${"$"}schema" : "http://json-schema.org/draft-04/schema#",
          "title" : "My Class",
          "type" : "object",
          "additionalProperties" : false,
          "properties" : {
            "a" : {
              "propertyOrder" : 1,
              "type" : "string",
              "title" : "A"
            },
            "b" : {
              "propertyOrder" : 2,
              "type" : "integer",
              "title" : "B"
            },
            "c" : {
              "propertyOrder" : 3,
              "type" : "number",
              "title" : "C"
            },
            "d" : {
              "propertyOrder" : 4,
              "type" : "integer",
              "format" : "utc-millisec",
              "title" : "D"
            },
            "e" : {
              "propertyOrder" : 5,
              "${"$"}ref" : "#/definitions/MySubClass",
              "title" : "E"
            }
          },
          "required" : [ "a", "b", "c", "d", "e" ],
          "definitions" : {
            "MySubClass" : {
              "type" : "object",
              "additionalProperties" : false,
              "properties" : {
                "a" : {
                  "propertyOrder" : 1,
                  "type" : "integer",
                  "title" : "A"
                }
              },
              "required" : [ "a" ]
            }
          }
        }
        """.trimIndent())
    }
}
