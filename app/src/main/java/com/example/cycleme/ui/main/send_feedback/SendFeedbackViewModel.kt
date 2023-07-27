package com.example.cycleme.ui.main.send_feedback

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cycleme.model.FeedbackData
import com.example.cycleme.repository.api.ApiConfig
import com.example.cycleme.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SendFeedbackViewModel : ViewModel() {

    private val _feedbackResult = MutableLiveData<Result<String>>()
    val feedbackResult: LiveData<Result<String>> get() = _feedbackResult

    fun sendFeedback(
        email: String, feedbackTitle: String, feedbackDetails: String
    ) {
        _feedbackResult.value = Result.Loading

        viewModelScope.launch {
            try {
                val feedbackData = FeedbackData(email, feedbackTitle, feedbackDetails)
                val response = withContext(Dispatchers.IO) {
                    ApiConfig.getApiServiceFeedback()
                        .sendFeedback(feedbackData)
                        .toString()
                }
                _feedbackResult.value = Result.Success(response)
            } catch (e: Exception) {
                _feedbackResult.value = Result.Error(e.message.toString())
            }
        }
    }

}