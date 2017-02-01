package ee.timeline

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import ee.common.ext.exists
import java.nio.file.Path

val mapper: ObjectMapper by lazy { jsonMapper() }
fun Path.loadTimeEvents(name: String = "timeEvents.json"): TimeEvents {
    val eeFile = resolve(name)
    val ret: TimeEvents = if (eeFile.exists()) mapper.readValue(eeFile.toFile()) else TimeEvents()
    return ret
}

fun TimeEvents.store(path: Path, name: String = "timeEvents.json") {
    mapper.writeValue(path.resolve(name).toFile(), this)
}

private fun jsonMapper(): ObjectMapper {
    val mapper = ObjectMapper().registerModule(KotlinModule())
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    return mapper
}
