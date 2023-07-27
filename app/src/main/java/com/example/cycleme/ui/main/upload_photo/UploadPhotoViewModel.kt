package com.example.cycleme.ui.main.upload_photo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cycleme.model.PredictResponse
import com.example.cycleme.repository.api.ApiConfig
import com.example.cycleme.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

class UploadPhotoViewModel : ViewModel() {
    private val _resultUploadPhotoResponse = MutableLiveData<Result<PredictResponse>>()
    val resultUploadPhotoResponse: LiveData<Result<PredictResponse>> get() = _resultUploadPhotoResponse

    fun uploadPhoto(
        file: MultipartBody.Part,
    ) {
        _resultUploadPhotoResponse.value = Result.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiConfig.getApiServicePredict().uploadImage(file)
                }
                _resultUploadPhotoResponse.value = Result.Success(response)
            } catch (e: Exception) {
                _resultUploadPhotoResponse.value = Result.Error(e.message.toString())
            }
        }
    }

}