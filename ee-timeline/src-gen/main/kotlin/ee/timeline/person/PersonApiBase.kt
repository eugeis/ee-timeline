package ee.timeline.person

import java.util.*

open class Author {
    companion object {
        val EMPTY = Author()
    }
    var firstName: String = ""
    var lastName: String = ""
    var birthsDate: Date = Date()
    var deathsDate: Date = Date()
    var quotes: MutableList<String> = arrayListOf()

    constructor(firstName: String = "", lastName: String = "", birthsDate: Date = Date(), deathsDate: Date = Date(), 
        quotes: MutableList<String> = arrayListOf()) {
        this.firstName = firstName
        this.lastName = lastName
        this.birthsDate = birthsDate
        this.deathsDate = deathsDate
        this.quotes = quotes
    }



}

fun Author?.orEmpty(): Author {
    return if (this != null) this else Author.EMPTY
}


