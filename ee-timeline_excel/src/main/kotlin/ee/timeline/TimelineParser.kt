package ee.excel

import ee.common.ext.addReturn
import ee.timeline.person.*
import ee.timeline.toTimelineJs3
import ee.timeline.toTimelineJs3ColorCss
import java.nio.file.Paths
import java.util.*

data class Link(val abbr: String = "", val name: String = "", val link: String = "", val fromYear: Int = -1,
    val toYear: Int = -1, val desc: String = "", val color: String = "")

class TimelineParser() {

    fun load(file: String): Phase {

        val ret = HashMap<String, Phase>()
        val excel = Excel.open(Paths.get(file))

        val linkEmpty = Link()

        val abbrToLink = hashMapOf<String, Link>()

        val phasen = excel.getSheet("Phasen")
        for (i in 1..phasen.count() - 1) {
            val row = phasen[i]
            if (row[0].trim().isNotEmpty()) {
                val link =
                    Link(row[0].trim(), row[1].trim(), row[2].trim(), row[3].toInt(), row[4].toInt(), row[6].trim(),
                        row[7].trim())
                abbrToLink[link.abbr] = link
            }
        }

        val subPhases = HashMap<String, Phase>()

        val theo = excel.getSheet("Vertreter")
        for (i in 1..theo.count() - 1) {
            val row = theo[i]
            val cell = row[0].trim()
            if (cell.isNotEmpty()) {
                val mainGroupLink = abbrToLink.getOrDefault(cell, linkEmpty)
                val group = ret.getOrPut(cell, {
                    Phase(LinkedName(name = mainGroupLink.name, link = mainGroupLink.link))
                })

                val phaseLink = abbrToLink[row[1].trim()]
                if (phaseLink == null) throw IllegalStateException(
                    "${row[1].trim()} ${row[2].trim()} is not matching phases")

                val phase = subPhases.getOrPut(phaseLink.name, {
                    group.phases.addReturn(Phase(LinkedName(name = phaseLink.name, link = phaseLink.link),
                        period = Period(start = phaseLink.fromYear, end = phaseLink.toYear),
                        description = phaseLink.desc, color = phaseLink.color))
                })

                if (row[7].trim().isNotBlank()) {
                    val birthPlace = abbrToLink.getOrElse(row[3].trim(), { Link(name = row[3].trim()) })
                    val deathPlace = abbrToLink.getOrElse(row[5].trim(), { Link(name = row[5].trim()) })

                    //0 Gr, 1 Gruppe, 2 Geburt, 3 Geburtsort, 4 Tod, 5 Tod Ort, 6 Vorname, 7 Nachname, 8 Link, 9 Zitat, 10 Jahr
                    val author = phase.authors.addReturn(Author(
                        name = PersonName(firstName = row[6].trim(), lastName = row[7].trim(), link = row[8].trim()),
                        birth = TimePoint(date = row[2].toDate(),
                            place = Place(name = birthPlace.name, link = birthPlace.link)),
                        death = TimePoint(date = row[4].toDate(),
                            place = Place(name = deathPlace.name, link = deathPlace.link)), fotoLink = row[11].trim()))
                    val quotes = row[9].trim()
                    if (quotes.isNotBlank()) {
                        author.quotes.add(row[9].trim())
                    }
                }
            }
        }

        excel.close()
        return Phase(name = LinkedName(name = "Zeittafel der Christlichen Theologie"),
            phases = ret.values.toMutableList()).sortByDates()
    }
}

fun main(args: Array<String>) {
    val phase = TimelineParser().load("/Users/ee/cloud/fecg_bs/ee/bibelschule/GM1/Zeitstrahl.xlsx")
    val timeEvents = phase.toTimeEvents()
    timeEvents.toTimelineJs3(Paths.get("/Users/ee/git/ee-bible/tl"), "theoline.json")
    timeEvents.toTimelineJs3ColorCss(Paths.get("/Users/ee/git/ee-bible/tl"), "theoline.css")
}

