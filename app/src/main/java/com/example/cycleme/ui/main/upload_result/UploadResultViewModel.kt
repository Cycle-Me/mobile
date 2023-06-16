package com.example.cycleme.ui.main.upload_result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cycleme.data.Result
import com.example.cycleme.model.PredictResponse
import com.example.cycleme.model.RecommendationRequest
import com.example.cycleme.model.RecommendationResponse
import com.example.cycleme.model.RecommendationResponseList
import com.example.cycleme.repository.api.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class UploadResultViewModel : ViewModel() {
    private val _resultRecommendation = MutableLiveData<Result<RecommendationResponseList>>()
    val resultRecommendation: LiveData<Result<RecommendationResponseList>> get() = _resultRecommendation

    fun getRecommendation(
        category: RecommendationRequest
    ) {
        _resultRecommendation.value = Result.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiConfig.getApiServiceRecommendation().postRecommendation(category)
                }
                val recommendationList = RecommendationResponseList(response.recommendationResponseList)
                _resultRecommendation.value = Result.Success(recommendationList)
            } catch (e: Exception) {
                _resultRecommendation.value = Result.Error(e.message.toString())
            }
        }
    }

}