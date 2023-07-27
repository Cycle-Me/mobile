package com.example.cycleme.ui.main.upload_result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cycleme.model.RecommendationRequest
import com.example.cycleme.model.RecommendationResponse
import com.example.cycleme.repository.api.ApiConfig
import com.example.cycleme.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UploadResultViewModel : ViewModel() {

    private val _resultRecommendation = MutableLiveData<Result<List<RecommendationResponse>>>()
    val resultRecommendation: LiveData<Result<List<RecommendationResponse>>> get() = _resultRecommendation

    fun getRecommendation(
        category: RecommendationRequest
    ) {
        _resultRecommendation.value = Result.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiConfig.getApiServiceRecommendation().postRecommendation(category)
                }
                val recommendationList: List<RecommendationResponse> = response
                _resultRecommendation.value = Result.Success(recommendationList)
            } catch (e: Exception) {
                _resultRecommendation.value = Result.Error(e.message.toString())
            }
        }
    }

}