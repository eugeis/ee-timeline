package ee.timeline.person

import java.net.URL
import java.util.Date




open class Author {
    val name: PersonName
    val birth: TimePoint
    val death: TimePoint
    val group: LinkedName
    val quotes: MutableList<String>


    constructor(name: PersonName = PersonName.EMPTY, birth: TimePoint = TimePoint.EMPTY, death: TimePoint = TimePoint.EMPTY, 
                group: LinkedName = LinkedName.EMPTY, quotes: MutableList<String> = arrayListOf()) {
        this.name = name
        this.birth = birth
        this.death = death
        this.group = group
        this.quotes = quotes
    }

    companion object {
        val EMPTY = Author()
    }
}


open class Group {
    val group: LinkedName
    val authors: MutableList<Author>


    constructor(group: LinkedName = LinkedName.EMPTY, authors: MutableList<Author> = arrayListOf()) {
        this.group = group
        this.authors = authors
    }

    companion object {
        val EMPTY = Group()
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



