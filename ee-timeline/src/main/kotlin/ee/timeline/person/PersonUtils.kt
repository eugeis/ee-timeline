package ee.timeline.person

import ee.timeline.*

fun Group.toTimeEvents(): TimeEvents {
    val ret = TimeEvents()
    val eras = hashMapOf<String, Era>()
    authors.forEach { author ->
        ret.events.add(
                TimeEvent(start = author.birth.date, end = author.death.date, group = author.group.name,
                        text = Text("${author.name.firstName} ${author.name.lastName}"), media = Media(author.name.link))

        )
        eras.getOrPut(author.group.name, { Era(text = Text(author.group.name)) })
    }
    return ret
}