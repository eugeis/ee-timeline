package ee.timeline.person

import java.util.*


open class Author {
    val name: PersonName
    val fotoLink: String
    val birth: TimePoint
    val death: TimePoint
    val quotes: MutableList<String>
    val workPeriod: Period


    constructor(name: PersonName = PersonName.EMPTY, fotoLink: String = "", birth: TimePoint = TimePoint.EMPTY,
        death: TimePoint = TimePoint.EMPTY, quotes: MutableList<String> = arrayListOf(),
        workPeriod: Period = Period.EMPTY) {
        this.name = name
        this.fotoLink = fotoLink
        this.birth = birth
        this.death = death
        this.quotes = quotes
        this.workPeriod = workPeriod
    }

    companion object {
        val EMPTY = Author()
    }
}


open class Linked {
    val link: String


    constructor(link: String = "") {
        this.link = link
    }

    companion object {
        val EMPTY = Linked()
    }
}


open class LinkedName : Linked {
    val name: String


    constructor(link: String = "", name: String = "") : super(link) {
        this.name = name
    }

    companion object {
        val EMPTY = LinkedName()
    }
}


open class Period {
    val caption: String
    val start: Int
    val end: Int


    constructor(caption: String = "", start: Int = 0, end: Int = 0) {
        this.caption = caption
        this.start = start
        this.end = end
    }

    companion object {
        val EMPTY = Period()
    }
}


open class PersonName : Linked {
    val firstName: String
    val lastName: String


    constructor(link: String = "", firstName: String = "", lastName: String = "") : super(link) {
        this.firstName = firstName
        this.lastName = lastName
    }

    companion object {
        val EMPTY = PersonName()
    }
}


open class Phase {
    val name: LinkedName
    val description: String
    val period: Period
    val color: String
    val authors: MutableList<Author>
    val phases: MutableList<Phase>


    constructor(name: LinkedName = LinkedName.EMPTY, description: String = "", period: Period = Period.EMPTY,
        color: String = "", authors: MutableList<Author> = arrayListOf(), phases: MutableList<Phase> = arrayListOf()) {
        this.name = name
        this.description = description
        this.period = period
        this.color = color
        this.authors = authors
        this.phases = phases
    }

    companion object {
        val EMPTY = Phase()
    }
}


open class Place : Linked {
    val name: String


    constructor(link: String = "", name: String = "") : super(link) {
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



