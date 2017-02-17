package ee.timeline.person

import java.net.URL
import java.util.Date




open class Author {
    var name: PersonName
    var birth: TimePoint
    var death: TimePoint
    var group: LinkedName
    var subGroup: LinkedName
    var quotes: MutableList<Era>


    constructor(name: PersonName = PersonName.EMPTY, birth: TimePoint = TimePoint.EMPTY, death: TimePoint = TimePoint.EMPTY, 
                group: LinkedName = LinkedName.EMPTY, subGroup: LinkedName = LinkedName.EMPTY, 
                quotes: MutableList<Era> = arrayListOf()) {
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
    var info: String
    var link: URL


    constructor(info: String = "", link: URL = URL("")) {
        this.info = info
        this.link = link
    }

    companion object {
        val EMPTY = Linked()
    }
}


open class LinkedName: Linked {
    var name: String


    constructor(info: String = "", link: URL = URL(""), name: String = "") {
        this.info = info
        this.link = link
        this.name = name
    }

    companion object {
        val EMPTY = LinkedName()
    }
}


open class PersonName: Linked {
    var firstName: String
    var lastName: String


    constructor(info: String = "", link: URL = URL(""), firstName: String = "", lastName: String = "") {
        this.info = info
        this.link = link
        this.firstName = firstName
        this.lastName = lastName
    }

    companion object {
        val EMPTY = PersonName()
    }
}


open class Place: Linked {
    var name: String


    constructor(info: String = "", link: URL = URL(""), name: String = "") {
        this.info = info
        this.link = link
        this.name = name
    }

    companion object {
        val EMPTY = Place()
    }
}


open class TimePoint {
    var date: Date
    var place: Place


    constructor(date: Date = Date(), place: Place = Place.EMPTY) {
        this.date = date
        this.place = place
    }

    companion object {
        val EMPTY = TimePoint()
    }
}



