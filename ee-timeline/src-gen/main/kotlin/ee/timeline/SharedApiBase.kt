package ee.timeline

import java.net.URL
import java.util.Date




open class TimeEvents {
    val title: TimeEvent
    val events: MutableList<Era>
    val eras: MutableList<Era>


    constructor(title: TimeEvent = TimeEvent.EMPTY, events: MutableList<Era> = arrayListOf(), eras: MutableList<Era> = arrayListOf()) {
        this.title = title
        this.events = events
        this.eras = eras
    }

    companion object {
        val EMPTY = TimeEvents()
    }
}


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


open class TimeEvent : Era {
    val group: String
    val media: Media


    constructor(start: Date = Date(), end: Date = Date(), text: Text = Text.EMPTY, group: String = "", media: Media = Media.EMPTY) : super(start, end, text) {
        this.group = group
        this.media = media
    }

    companion object {
        val EMPTY = TimeEvent()
    }
}


open class Text {
    val title: String
    val text: String


    constructor(title: String = "", text: String = "") {
        this.title = title
        this.text = text
    }

    companion object {
        val EMPTY = Text()
    }
}


open class Media {
    val url: URL
    val caption: String
    val credit: String
    val thumbnail: String


    constructor(url: URL = URL(""), caption: String = "", credit: String = "", thumbnail: String = "") {
        this.url = url
        this.caption = caption
        this.credit = credit
        this.thumbnail = thumbnail
    }

    companion object {
        val EMPTY = Media()
    }
}



