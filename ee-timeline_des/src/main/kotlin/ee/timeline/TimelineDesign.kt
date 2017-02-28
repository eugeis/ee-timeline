package ee.timeline

import ee.design.*
import ee.lang.*

object Timeline : Comp({ artifact("ee-timeline").namespace("ee.timeline") }) {
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
            val headline = prop()
            val text = prop()
        }

        object Media : Basic() {
            val url = prop { type(n.Url).value("""URL("http://")""") }
            val caption = prop()
            val credit = prop()
            val thumbnail = prop()
        }

        object TimeEvent : Entity({ superUnit(Era) }) {
            val group = prop()
            val media = prop(Media)
            val range = prop(n.String)
        }
    }

    object person : Module() {
        object Linked : Basic() {
            val link = prop { type(n.Url).value("""URL("http://")""") }
        }

        object LinkedName : Basic({ superUnit(Linked) }) {
            val name = prop()
        }

        object PersonName : Basic({ superUnit(Linked) }) {
            val firstName = prop()
            val lastName = prop()
        }

        object Place : Basic({ superUnit(Linked) }) {
            val name = prop()
        }

        object TimePoint : Basic() {
            val date = prop(n.Date)
            val place = prop(Place)
        }

        object Phase : Entity() {
            val name = prop(LinkedName)
            val from = prop(n.Date)
            val to = prop(n.Date)
            val range = prop(n.String)
            val authors = prop(n.List.GT(Author))
            val phases = prop(n.List.GT(Phase))
        }

        object Author : Entity() {
            val name = prop(PersonName)
            val birth = prop(TimePoint)
            val death = prop(TimePoint)
            val quotes = prop(n.List.GT(n.String))
        }
    }
}
