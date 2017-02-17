package ee.timeline

import java.net.URL
import java.util.Date




open class TimeEvents {
    var title: TimeEvent
    var events: MutableList<Era>
    var eras: MutableList<Era>


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
    var start: Date
    var end: Date
    var text: Text


    constructor(start: Date = Date(), end: Date = Date(), text: Text = Text.EMPTY) {
        this.start = start
        this.end = end
        this.text = text
    }

    companion object {
        val EMPTY = Era()
    }
}


open class TimeEvent: Era {
    var group: String
    var media: Media


    constructor(start: Date = Date(), end: Date = Date(), text: Text = Text.EMPTY, group: String = "", media: Media = Media.EMPTY) {
        this.start = start
        this.end = end
        this.text = text
        this.group = group
        this.media = media
    }

    companion object {
        val EMPTY = TimeEvent()
    }
}


open class Text {
    var title: String
    var text: String


    constructor(title: String = "", text: String = "") {
        this.title = title
        this.text = text
    }

    companion object {
        val EMPTY = Text()
    }
}


open class Media {
    var url: URL
    var caption: String
    var credit: String
    var thumbnail: String


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



