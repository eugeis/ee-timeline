package ee.timeline.person

import java.net.URL
import java.util.Date


open class Author {
    val name: PersonName
    val birth: TimePoint
    val death: TimePoint
    val group: LinkedName
    val subGroup: LinkedName
    val quotes: MutableList<String>


    constructor(name: PersonName = PersonName.EMPTY, birth: TimePoint = TimePoint.EMPTY, death: TimePoint = TimePoint.EMPTY,
                group: LinkedName = LinkedName.EMPTY, subGroup: LinkedName = LinkedName.EMPTY,
                quotes: MutableList<String> = arrayListOf()) {
        this.name = name
        this.birth = birth
        this.death = death
        this.group = group
        this.subGroup = subGroup
        this.quotes = quotes
    }

    companion object {
        val EMPTY = Author()
    }
}


open class Linked {
    val link: URL


    constructor(link: URL = URL("")) {
        this.link = link
    }

    companion object {
        val EMPTY = Linked()
    }
}


open class LinkedName : Linked {
    val name: String


    constructor(link: URL = URL(""), name: String = "") : super(link) {
        this.name = name
    }

    companion object {
        val EMPTY = LinkedName()
    }
}


open class PersonName : Linked {
    val firstName: String
    val lastName: String


    constructor(link: URL = URL(""), firstName: String = "", lastName: String = "") : super(link) {
        this.firstName = firstName
        this.lastName = lastName
    }

    companion object {
        val EMPTY = PersonName()
    }
}


open class Place : Linked {
    val name: String


    constructor(link: URL = URL(""), name: String = "") : super(link) {
        this.name = name
    }

    companion object {
        val EMPTY = Place()
    }
}


open class TimePoint {
    val date: Date
    val place: Place


    constructor(date: Date = Date(), place: Place = Place.EMPTY) {
        this.date = date
        this.place = place
    }

    companion object {
        val EMPTY = TimePoint()
    }
}



