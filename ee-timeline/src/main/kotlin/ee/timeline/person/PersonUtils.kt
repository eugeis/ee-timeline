package ee.timeline.person

import ee.timeline.*

fun Phase.toTimeEvents(): TimeEvents {
    val ret = TimeEvents(title = TimeEvent(text = Text(name.name)))

    phases.forEach { groupPhase ->
        groupPhase.phases.forEach { phase ->
            ret.events.add(
                    TimeEvent(start = phase.from, end = phase.to, range = phase.range, group = groupPhase.name.name,
                            text = Text("${phase.name.name}"), media = Media(phase.name.link))
            )
        }

        authors.forEach { author ->
            ret.events.add(
                    TimeEvent(start = author.birth.date, end = author.death.date, group = groupPhase.name.name,
                            text = Text("${author.name.firstName} ${author.name.lastName}"), media = Media(author.name.link))
            )
        }
    }

    return ret
}