package com.example.cycleme.ui.main.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cycleme.model.LogoutResponse
import com.example.cycleme.repository.api.ApiConfig
import com.example.cycleme.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountViewModel: ViewModel() {

    private val _resultLogoutResponse = MutableLiveData<Result<LogoutResponse>>()
    val resultLogoutResponse: LiveData<Result<LogoutResponse>> get() = _resultLogoutResponse

    fun postLogout() {
        _resultLogoutResponse.value = Result.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiConfig.getApiServiceMain().logout()
                }
                _resultLogoutResponse.value = Result.Success(response)
            } catch (e: Exception) {
                _resultLogoutResponse.value = Result.Error(e.message.toString())
            }
        }
    }
}