package com.sample.characterviewer.data.model

import com.google.gson.annotations.SerializedName

data class Topic (
    @SerializedName("Text")  var Text: String,
    @SerializedName("Icon")  var Icon: TopicIcon
) : java.io.Serializable {
    val characterName: String
        get() = Text.split(" - ")[0]
    val characterDescription: String
        get() = Text.split(" - ")[1]
}