package com.example.cycleme.ui.main.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cycleme.model.StoriesResponse
import com.example.cycleme.repository.api.ApiConfig
import com.example.cycleme.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedViewModel : ViewModel() {

    private val _responseStories = MutableLiveData<Result<List<StoriesResponse>>>()
    val responseStories: LiveData<Result<List<StoriesResponse>>> get() = _responseStories

    fun getAllStories() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiConfig.getApiServiceMain().getStories()
                }
                _responseStories.value = Result.Success(response)
            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
                e.printStackTrace()
                _responseStories.value = Result.Error(e.message.toString())
            }
        }
    }

}