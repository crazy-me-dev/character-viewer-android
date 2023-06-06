package com.sample.characterviewer.data.model

data class TopicIcon(
    var Width: String,
    var Height: String,
    var URL: String,
): java.io.Serializable {
    val iconURLString: String
        get() = if (URL =="") "" else "https://duckduckgo.com" + URL
}