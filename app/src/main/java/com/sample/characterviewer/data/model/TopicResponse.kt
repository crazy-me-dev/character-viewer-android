package com.sample.characterviewer.data.model

data class TopicResponse (
    var AbstractSource: String,
    var AbstractURL: String,
    var RelatedTopics: ArrayList<Topic>
)