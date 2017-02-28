package ee.excel

import ee.common.ext.addReturn
import ee.timeline.TimelineJs3JsonMapper
import ee.timeline.person.*
import ee.timeline.toTimelineJs3
import java.net.URL
import java.nio.file.Paths
import java.util.*

data class Link(val abbr: String = "", val name: String = "", val link: URL = URL("http://"))

class TimelineParser() {

    fun load(file: String): Phase {

        val ret = HashMap<String, Phase>()
        val excel = Excel.open(file)

        val linkEmpty = Link()

        val abbrToLink = hashMapOf<String, Link>()

        val links = excel.getSheet("Links")
        for (i in 1..links.count()) {
            val row = links[i]
            val link = Link(row[0].trim(), row[1].trim(), row[2].toUrl())
            abbrToLink[link.abbr] = link
        }

        val subPhases = HashMap<String, Phase>()

        val theo = excel.getSheet("Theologen")
        for (i in 1..theo.count()) {
            val row = theo[i]
            val cell = row[0].trim()
            if (cell.isNotEmpty()) {
                val mainGroupLink = abbrToLink.getOrDefault(cell, linkEmpty)
                val group = ret.getOrPut(cell, {
                    Phase(LinkedName(name = mainGroupLink.name, link = mainGroupLink.link))
                })

                val phaseLink = abbrToLink.getOrElse(row[1].trim(), { Link(name = row[1].trim()) })

                val phase = subPhases.getOrPut(phaseLink.name, {
                    group.phases.addReturn(
                            Phase(LinkedName(name = phaseLink.name, link = phaseLink.link), range = row[10].trim()))
                })

                val birthPlace = abbrToLink.getOrElse(row[3].trim(), { Link(name = row[3].trim()) })
                val deathPlace = abbrToLink.getOrElse(row[5].trim(), { Link(name = row[5].trim()) })

                //0 Gr, 1 Gruppe, 2 Geburt, 3 Geburtsort, 4 Tod, 5 Tod Ort, 6 Vorname, 7 Nachname, 8 Link, 9 Zitat, 10 Jahr
                val author = phase.authors.addReturn(
                        Author(name = PersonName(firstName = row[6].trim(), lastName = row[7].trim(), link = row[8].toUrl()),
                                birth = TimePoint(date = row[2].toDate(), place = Place(name = birthPlace.name, link = birthPlace.link)),
                                death = TimePoint(date = row[4].toDate(), place = Place(name = deathPlace.name, link = deathPlace.link))
                        ))
                author.quotes.add(row[9].trim())
            }
        }

        excel.close()
        return Phase(name = LinkedName(name = "Zeitstrahl der Strömungen der Theologie"), phases = ret.values.toMutableList())
    }
}

fun main(args: Array<String>) {
    val phase = TimelineParser().load("/Users/ee/cloud/fecg_bs/ee/bibelschule/GM1/Zeitstahl.xlsx")
    val timeEvents = phase.toTimeEvents()
    timeEvents.toTimelineJs3(Paths.get("/Users/ee/git/angular-timelinejs3/dist/demo"))
}

