package com.example.cycleme.ui.main.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cycleme.model.LoginRequest
import com.example.cycleme.model.LoginResponse
import com.example.cycleme.repository.api.ApiConfig
import com.example.cycleme.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel: ViewModel() {

    private val _resultLoginResponse = MutableLiveData<Result<LoginResponse>>()
    val resultLoginResponse: LiveData<Result<LoginResponse>> get() = _resultLoginResponse

    fun postLogin(request: LoginRequest) {
        _resultLoginResponse.value = Result.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiConfig.getApiServiceMain().postLogin(request)
                }
                _resultLoginResponse.value = Result.Success(response)
            } catch (e: Exception) {
                _resultLoginResponse.value = Result.Error(e.message.toString())
            }
        }
    }
}