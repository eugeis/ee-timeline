package ee.timeline

import ee.design.Basic
import ee.design.Comp
import ee.design.Entity
import ee.design.Module
import ee.lang.GT
import ee.lang.n
import ee.lang.prop

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
            val url = prop { type(n.String) }
            val caption = prop()
            val credit = prop()
            val thumbnail = prop()
        }

        object Background : Basic() {
            val url = prop { type(n.String).nullable(true) }
            val color = prop()
        }

        object TimeEvent : Entity({ superUnit(Era) }) {
            val group = prop()
            val media = prop(Media)
            val range = prop(n.String)
            val background = prop(Background)
        }
    }

    object person : Module() {
        object Linked : Basic() {
            val link = prop { type(n.String) }
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

        object Period : Basic() {
            val caption = prop(n.String)
            val start = prop(n.Int)
            val end = prop(n.Int)
        }

        object Phase : Entity() {
            val name = prop(LinkedName)
            val period = prop(Period)
            val authors = prop(n.List.GT(Author))
            val phases = prop(n.List.GT(Phase))
        }

        object Author : Entity() {
            val name = prop(PersonName)
            val fotoLink = prop()
            val birth = prop(TimePoint)
            val death = prop(TimePoint)
            val quotes = prop(n.List.GT(n.String))
            val workPeriod = prop(Period)
        }
    }
}
