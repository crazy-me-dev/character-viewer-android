package com.sample.characterviewer.network

import com.sample.characterviewer.data.model.TopicResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiInterface {
    @GET()
    fun fetchAllTopics(@Url url: String): Call<TopicResponse>
}