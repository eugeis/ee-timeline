package ee.timeline.person

import ee.common.ext.ifElse
import ee.common.ext.joinSurroundIfNotEmptyToString
import ee.common.ext.then
import ee.common.ext.toUrlKey
import ee.timeline.*
import java.util.*

private val cal = Calendar.getInstance()
fun Int.toYearStartDate(): Date {
    cal.set(this, 0, 1)
    return cal.time
}

fun Int.toYearEndDate(): Date {
    cal.set(this, 11, 31)
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

fun Phase.sortByDates(): Phase {
    phases.sortBy { it.period.start }
    authors.sortBy { it.birth.date }
    phases.forEach { it.sortByDates() }
    return this
}

fun Phase.fillTimeEvents(timeEvents: TimeEvents, parent: Phase? = null): TimeEvents {
    val group = parent?.name?.name ?: ""
    val groupAuthors = when (group) {
        "Liberale" -> "Spekulationstheologen"
        "bibeltreue" -> "Offenbarungstheologen"
        else -> {
            "$group Vertreter"
        }
    }

    authors.forEach { author ->
        val fullName = author.fullName()
        val photo = author.fotoLink.isNotBlank().then {
            """<img class="im" src="${author.fotoLink}" height="160px"/>"""
        }
        val quotes = author.quotes.isNotEmpty().then {
            """<q class="q">${author.quotes.first()}</q>"""
        }

        timeEvents.events.add(TimeEvent(id = fullName.toUrlKey(), start = author.birth.date, end = author.death.date,
            group = groupAuthors, markerBackground = background(color), media = Media(author.name.link), text = Text(
                headline = author.name.link.isNotBlank().ifElse(
                    { """<a href="${author.name.link}" target="_blank">$fullName</a>""" },
                    { "${author.name.firstName} ${author.name.lastName}" }), text = """
    <div class="ph">
        <a href="#event-${name.name.toUrlKey()}" onclick="timeline.goToId('${name.name.toUrlKey()}');">${name.name}</a>
    </div>$photo$quotes""")))
    }

    phases.forEach { phase ->
        val group = name.name
        val groupSchulen = "$group Schulen"

        val authors = phase.authors.map { author ->
            val fullName = author.fullName()
            """<a href="#event-${fullName.toUrlKey()}" onclick="timeline.goToId('${fullName.toUrlKey()}');">$fullName</a>"""
        }.joinSurroundIfNotEmptyToString(prefix = """<br><p>Vertreter:</p><ul class="author">""",
            postfix = "</ul>",
            separator = "\n    ") { "<li>$it</li>" }
        val description = phase.description.split('\n')
            .joinToString(prefix = "<p>Charakteristika:</p>\n<ul>", postfix = "</ul>",
                separator = "\n    ") { "<li>$it</li>" }
        timeEvents.events.add(TimeEvent(id = phase.name.name.toUrlKey(), start = phase.period.start.toYearStartDate(),
            end = phase.period.end.toYearEndDate(), group = groupSchulen, markerBackground = background(phase.color),
            text = Text(headline = phase.name.link.isNotBlank().ifElse(
                { """<a href="${phase.name.link}" target="_blank">${phase.name.name}</a>""" },
                { "${phase.name.name}" }), text = "$description$authors"),
            media = phase.name.link.startsWith("https://de.wikipedia.").ifElse({ Media(phase.name.link) },
                { Media.EMPTY })))
        phase.fillTimeEvents(timeEvents, this)
    }
    return timeEvents
}

private fun background(color: String) = Background(color = color)

fun Author.fullName() = name.firstName.isBlank().ifElse({ name.lastName }, { "${name.firstName} ${name.lastName}" })

