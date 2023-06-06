package com.sample.characterviewer.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.sample.characterviewer.R
import com.sample.characterviewer.data.model.Topic
import com.sample.characterviewer.data.TopicsRepository

class TopicsListViewModel(application: Application): AndroidViewModel(application) {

    private var repository: TopicsRepository? = null
    var topicsListLiveData: LiveData<ArrayList<Topic>>? = null

    var filteredTopicsListLiveData: MutableLiveData<ArrayList<Topic>>? = null
    var filteringKeyword: String = ""

    init {
        val apiUrl = application.resources.getString(R.string.api_url)
        repository = TopicsRepository(apiUrl)
        topicsListLiveData = MutableLiveData()
        filteredTopicsListLiveData = MutableLiveData()
    }

    fun fetchAllTopics() {
        topicsListLiveData = repository?.fetchAllTopics()
    }

    fun filterTopics(keyword: String) {
        Log.v("Filter", "filtering " + keyword + " in " + topicsListLiveData?.value?.size)
        filteringKeyword = keyword

        if (keyword == "") {
            filteredTopicsListLiveData?.value = topicsListLiveData?.value
            return
        }

        val filtered = topicsListLiveData?.value?.filter { character ->
            character.characterName.lowercase().contains(keyword.lowercase())
        }

        filteredTopicsListLiveData?.value = filtered as? ArrayList<Topic>
    }

}