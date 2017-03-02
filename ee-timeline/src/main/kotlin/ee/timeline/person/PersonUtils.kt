package ee.timeline.person

import ee.common.ext.ifElse
import ee.common.ext.then
import ee.timeline.*
import java.util.*

private val cal = Calendar.getInstance()
fun Int.toYearStartDate(): Date {
    cal.set(this, 1, 1)
    return cal.time
}

fun Int.toYearEndDate(): Date {
    cal.set(this, 12, 31)
    return cal.time
}

val phaseBackground = Background()
val authorBackground = Background()

fun Phase.toTimeEvents(): TimeEvents {
    val ret = TimeEvents(title = TimeEvent(text = Text(headline = name.name, text = """
    <div class="intro">
        <div>
            <p><i>Kreative Facharbeit für den Kurs
            <a href="http://www.lza.de/kurse/gm/gm-1-b-zeitzonen-zeitzeugen-zeitgeist-2017" target="_blank">
            ZEITZONEN, ZEITZEUGEN, ZEITGEIST (GM 1-B)</a>. Eugen Eisler</i></p>

            <ul>
                <li>Dozent: Dr. Wolfgang Schnabel</li>
                <li>Fach: Geschichte & Methoden</li>
                <li>Kurs: Zeitzonen, Zeitzeugen, Zeitgeist –Theologie und Entwicklung der Praktischen Theologie (GM 1)</li>
            </ul>
            <br>
            <p>In dieser interaktiven Zeittafel sind die wesentlichen Strömungen der christlichen Theologie und
            deren wichtigsten Vertreter in deutschsprachigen Raum zusammengefasst.<br></p>
            <p><i>Hinweis: Die untere Zeitleiste lässt sich auf der horizontalen Achse bewegen und somit zu dem gewünschten Zeitraum navigieren.
            Über die '+' und '-' Schaltflächen auf der Linke Seite lässt sich der Zeitraster vergrössern bzw. verkleinern.</i></p>
        </div>

    </div>
""")))
    phases[0].fillTimeEvents(ret)
    phases[1].fillTimeEvents(ret)
    return ret
}

fun Phase.fillTimeEvents(timeEvents: TimeEvents, parent: Phase? = null): TimeEvents {
    val group = parent?.name?.name ?: ""
    authors.forEach { author ->
        val photo = author.fotoLink.isNotBlank().then {
            """<img class="im" src="${author.fotoLink}" height="160px"/>"""
        }
        val quotes = author.quotes.isNotEmpty().then {
            """<q class="q">${author.quotes.first()}</q>"""
        }

        timeEvents.events.add(
                TimeEvent(start = author.birth.date, end = author.death.date,
                        group = group, background = authorBackground, media = Media(author.name.link),
                        text = Text(headline = "${author.name.firstName} ${author.name.lastName}",
                                text = """<div class="ph"><a href="${name.link}" target="_blank">${name.name}</a></div>$photo$quotes"""))
        )
    }

    phases.forEach { phase ->
        val group = name.name

        val authors = phase.authors.map {
            """<div class="author"><a href="${it.name.link}" target="_blank">${it.name.firstName} ${it.name.lastName}</a></div>"""
        }.joinToString("\n")
        timeEvents.events.add(
                TimeEvent(start = phase.period.start.toYearStartDate(), end = phase.period.end.toYearEndDate(),
                        range = phase.period.caption, group = group, background = phaseBackground,
                        text = Text(headline = "${phase.name.name}", text = authors), media = Media(phase.name.link)))
        phase.fillTimeEvents(timeEvents, this)
    }
    return timeEvents
}