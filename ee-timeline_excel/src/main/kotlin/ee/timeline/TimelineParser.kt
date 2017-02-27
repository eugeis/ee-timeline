package ee.excel

import ee.common.ext.addReturn
import ee.timeline.person.*
import java.util.*


class TimelineParser {

    fun load(file: String): Map<String, Group> {

        val ret = HashMap<String, Group>()

        val excel = Excel.open(file)

        val sheet = excel.getSheet("Theologen")
        sheet.forEach { row ->

            val cell = row[1].trim()
            if (cell.isNotEmpty() && !cell.equals("Gr", true)) {
                val group = ret.getOrPut(cell, { Group(LinkedName(name = cell)) })
                //1 Gr, 2 Untergruppe, 3 Geburt, 4 Geburtsort, 5 Tod, 6 Tod Ort, 7 Vorname, 8 Nachname, 9 Link, 10 Zitat, 11 Jahr
                val author = group.authors.addReturn(
                        Author(name = PersonName(firstName = row[7].trim(), lastName = row[8].trim(), link = row[9].toUrl()),
                                birth = TimePoint(date = row[3].toDate(), place = Place(name = row[4].trim())),
                                death = TimePoint(date = row[5].toDate(), place = Place(name = row[6].trim())),
                                group = LinkedName(name = row[2].trim(), link = row[9].toUrl())
                        ))
                author.quotes.add(row[10].trim())
            }
        }
        excel.close()
        return ret
    }
}

fun main(args: Array<String>) {
    val groups = TimelineParser().load("/Users/ee/cloud/fecg_bs/cloud/bibelschule/GM1/Zeitstahl.xlsx")
    println(groups)
}

