package ee.timeline

import java.net.URL
import java.util.Date




open class Era {
    val start: Date
    val end: Date
    val text: Text


    constructor(start: Date = Date(), end: Date = Date(), text: Text = Text.EMPTY) {
        this.start = start
        this.end = end
        this.text = text
    }

    companion object {
        val EMPTY = Era()
    }
}


open class Media {
    val url: URL
    val caption: String
    val credit: String
    val thumbnail: String


    constructor(url: URL = URL("http://"), caption: String = "", credit: String = "", thumbnail: String = "") {
        this.url = url
        this.caption = caption
        this.credit = credit
        this.thumbnail = thumbnail
    }

    companion object {
        val EMPTY = Media()
    }
}


open class Text {
    val headline: String
    val text: String


    constructor(headline: String = "", text: String = "") {
        this.headline = headline
        this.text = text
    }

    companion object {
        val EMPTY = Text()
    }
}


open class TimeEvent : Era {
    val group: String
    val media: Media
    val range: String


    constructor(start: Date = Date(), end: Date = Date(), text: Text = Text.EMPTY, group: String = "", media: Media = Media.EMPTY, 
                range: String = "") : super(start, end, text) {
        this.group = group
        this.media = media
        this.range = range
    }

    companion object {
        val EMPTY = TimeEvent()
    }
}


open class TimeEvents {
    val title: TimeEvent
    val events: MutableList<TimeEvent>
    val eras: MutableList<Era>


    constructor(title: TimeEvent = TimeEvent.EMPTY, events: MutableList<TimeEvent> = arrayListOf(), 
                eras: MutableList<Era> = arrayListOf()) {
        this.title = title
        this.events = events
        this.eras = eras
    }

    companion object {
        val EMPTY = TimeEvents()
    }
}



