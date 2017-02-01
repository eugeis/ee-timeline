package ee.timeline

import java.util.*

data class Text(var title: String = "", var text: String = "") {
    companion object {
        val EMPTY = Text()
    }



}

fun Text?.orEmpty(): Text {
    return if (this != null) this else Text.EMPTY
}

data class Media(var url: String = "", var caption: String = "", var credit: String = "", var thumbnail: String = "") {
    companion object {
        val EMPTY = Media()
    }



}

fun Media?.orEmpty(): Media {
    return if (this != null) this else Media.EMPTY
}

open class TimeEvents {
    companion object {
        val EMPTY = TimeEvents()
    }
    var title: TimeEvent = TimeEvent.EMPTY
    var events: MutableList<TimeEvent> = arrayListOf()
    var eras: MutableList<Era> = arrayListOf()

    constructor(title: TimeEvent = TimeEvent.EMPTY, events: MutableList<TimeEvent> = arrayListOf(), 
        eras: MutableList<Era> = arrayListOf()) {
        this.title = title
        this.events = events
        this.eras = eras
    }



}

fun TimeEvents?.orEmpty(): TimeEvents {
    return if (this != null) this else TimeEvents.EMPTY
}

open class Era {
    companion object {
        val EMPTY = Era()
    }
    var start: Date = Date()
    var end: Date = Date()
    var text: Text = Text.EMPTY

    constructor(start: Date = Date(), end: Date = Date(), text: Text = Text.EMPTY) {
        this.start = start
        this.end = end
        this.text = text
    }



}

fun Era?.orEmpty(): Era {
    return if (this != null) this else Era.EMPTY
}

open class TimeEvent : Era {
    companion object {
        val EMPTY = TimeEvent()
    }
    var group: String = ""
    var media: Media = Media.EMPTY

    constructor(start: Date = Date(), end: Date = Date(), text: Text = Text.EMPTY, group: String = "", media: Media = Media.EMPTY): super(start, end, text) {
        this.group = group
        this.media = media
    }



}

fun TimeEvent?.orEmpty(): TimeEvent {
    return if (this != null) this else TimeEvent.EMPTY
}


