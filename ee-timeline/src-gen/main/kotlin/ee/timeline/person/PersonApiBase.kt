package ee.timeline.person

import java.net.URL
import java.util.Date




open class Author {
    val name: PersonName
    val birth: TimePoint
    val death: TimePoint
    val quotes: MutableList<String>


    constructor(name: PersonName = PersonName.EMPTY, birth: TimePoint = TimePoint.EMPTY, death: TimePoint = TimePoint.EMPTY, 
                quotes: MutableList<String> = arrayListOf()) {
        this.name = name
        this.birth = birth
        this.death = death
        this.quotes = quotes
    }

    companion object {
        val EMPTY = Author()
    }
}


open class Linked {
    val link: URL


    constructor(link: URL = URL("http://")) {
        this.link = link
    }

    companion object {
        val EMPTY = Linked()
    }
}


open class LinkedName : Linked {
    val name: String


    constructor(link: URL = URL("http://"), name: String = "") : super(link) {
        this.name = name
    }

    companion object {
        val EMPTY = LinkedName()
    }
}


open class PersonName : Linked {
    val firstName: String
    val lastName: String


    constructor(link: URL = URL("http://"), firstName: String = "", lastName: String = "") : super(link) {
        this.firstName = firstName
        this.lastName = lastName
    }

    companion object {
        val EMPTY = PersonName()
    }
}


open class Phase {
    val name: LinkedName
    val from: Date
    val to: Date
    val range: String
    val authors: MutableList<Author>
    val phases: MutableList<Phase>


    constructor(name: LinkedName = LinkedName.EMPTY, from: Date = Date(), to: Date = Date(), range: String = "", 
                authors: MutableList<Author> = arrayListOf(), phases: MutableList<Phase> = arrayListOf()) {
        this.name = name
        this.from = from
        this.to = to
        this.range = range
        this.authors = authors
        this.phases = phases
    }

    companion object {
        val EMPTY = Phase()
    }
}


open class Place : Linked {
    val name: String


    constructor(link: URL = URL("http://"), name: String = "") : super(link) {
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



