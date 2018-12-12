import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.kjetland.jackson.jsonSchema.JsonSchemaConfig
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator

fun getJsonSchemaFromClass(clazz: Class<*>): String {
    val mapper = jacksonObjectMapper()
    val schema = JsonSchemaGenerator(mapper, JsonSchemaConfig.html5EnabledSchema())
        .generateJsonSchema(clazz)
    return mapper
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(schema)
}
