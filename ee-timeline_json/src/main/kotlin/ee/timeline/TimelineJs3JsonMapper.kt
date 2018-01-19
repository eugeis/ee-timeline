package ee.timeline

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.cfg.MapperConfig
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.nio.file.Path
import java.text.SimpleDateFormat
import java.util.*

class TimeEventSerializer : StdSerializer<TimeEvent>(TimeEvent::class.java) {
    override fun serialize(value: TimeEvent?, gen: JsonGenerator, provider: SerializerProvider) {

    }
}

class ExpandDateSerializer : StdSerializer<Date>(Date::class.java) {
    private val d = SimpleDateFormat("dd")
    private val m = SimpleDateFormat("MM")
    private val y = SimpleDateFormat("yyyy")

    override fun serialize(value: Date?, gen: JsonGenerator, provider: SerializerProvider) {
        if (value != null) {
            //gen.writeRawValue("""{ "year": "${y.format(value)}", "month": "${m.format(value)}", "day": "${d.format(value)}" }""")
            gen.writeRawValue("""{ "year": "${y.format(value)}" }""")
        }
    }
}

private val mapper = TimelineJs3JsonMapper()

class TimelineJs3JsonMapper : ObjectMapper() {
    init {
        registerModule(KotlinModule())
        enable(SerializationFeature.INDENT_OUTPUT);
        propertyNamingStrategy = PropertyNamingStrategy2()
        val module = SimpleModule()
        module.addSerializer(Date::class.java, ExpandDateSerializer())
        //module.addSerializer(TimeEvent::class.java, TimeEventSerializer())
        registerModule(module)
    }
}

class PropertyNamingStrategy2 : PropertyNamingStrategy() {
    val nameToChange =
        hashMapOf("start" to "start_date", "end" to "end_date", "range" to "display_date", "id" to "unique_id")

    override fun nameForGetterMethod(config: MapperConfig<*>, method: AnnotatedMethod, defaultName: String): String {
        return nameToChange[defaultName] ?: super.nameForGetterMethod(config, method, defaultName)
    }
}

fun TimeEvents.toTimelineJs3(path: Path, name: String = "TimetableJS3.json") {
    mapper.writeValue(path.resolve(name).toFile(), this)
}

fun TimeEvents.toTimelineJs3ColorCss(path: Path, name: String = "TimetableJS3Color.css") {
    path.resolve(name).toFile().printWriter().use { out ->
        this.events.forEach { event ->
            out.println("""
#${event.id}-marker > .tl-timemarker-content-container {
    background-color: ${event.markerBackground.color};
}""")
        }
    }
}