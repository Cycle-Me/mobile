package com.example.cycleme.ui.main.upload_photo_feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cycleme.model.UploadFeedResponse
import com.example.cycleme.repository.api.ApiConfig
import com.example.cycleme.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

class UploadPhotoFeedViewModel : ViewModel() {

    private val _resultUploadPhotoFeedResponse = MutableLiveData<Result<UploadFeedResponse>>()
    val resultUploadPhotoFeedResponse: LiveData<Result<UploadFeedResponse>> get() = _resultUploadPhotoFeedResponse

    fun uploadFeed(
        description: String, file: MultipartBody.Part,
    ) {
        _resultUploadPhotoFeedResponse.value = Result.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiConfig.getApiServiceMain().uploadFeed(description, file)
                }
                _resultUploadPhotoFeedResponse.value = Result.Success(response)
                Log.d("FeedSuccess", response.toString())
            } catch (e: Exception) {
                _resultUploadPhotoFeedResponse.value = Result.Error(e.message.toString())
                Log.d("FeedError", e.message.toString())
            }
        }
    }
}