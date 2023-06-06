package com.sample.characterviewer.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sample.characterviewer.data.model.Topic
import com.sample.characterviewer.data.model.TopicResponse
import com.sample.characterviewer.network.ApiClient
import com.sample.characterviewer.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopicsRepository(val apiUrl: String) {

    private var apiInterface: ApiInterface? = null

    init {
        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
    }

    fun fetchAllTopics(): LiveData<ArrayList<Topic>> {
        val data = MutableLiveData<ArrayList<Topic>>()

        apiInterface?.fetchAllTopics(apiUrl)?.enqueue(object: Callback<TopicResponse>{

            override fun onFailure(call: Call<TopicResponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<TopicResponse>, response: Response<TopicResponse>) {
                var res = response.body()
                if (response.code() == 200 && res != null) {
                    data.value = res.RelatedTopics
                } else {
                    data.value = null
                }
            }
        })

        return data
    }
}