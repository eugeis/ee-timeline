package ee.timeline

import ee.dsl.data.*
import ee.dsl.*

object TimelineDesign : Model({ namespace("ee") }) {
    object Timeline : Comp() {
        object shared : Module() {

            object TimeEvents : Entity() {
                val title = prop(TimeEvent)
                val events = prop(n.List.GT(TimeEvent))
                val eras = prop(n.List.GT(Era))
            }

            object Era : Entity() {
                val start = prop(n.Date)
                val end = prop(n.Date)
                val text = prop(Text)
            }

            object Text : Basic() {
                val title = prop()
                val text = prop()
            }

            object Media : Basic() {
                val url = prop()
                val caption = prop()
                val credit = prop()
                val thumbnail = prop()
            }

            object TimeEvent : Entity({ superUnit(Era) }) {
                val group = prop()
                val media = prop(Media)
            }
        }

        object person : Module() {
            object Author : Entity() {
                val firstName = prop()
                val lastName = prop()
                val birthsDate = prop(n.Date)
                val deathsDate = prop(n.Date)
                val quotes = prop(n.List.GT())
            }
        }
    }
}

fun model(): Model {
    n.initObjectTree()
    return TimelineDesign.initObjectTree()
}
