package com.example.cycleme.ui.main.auth.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cycleme.model.RegisterRequest
import com.example.cycleme.model.RegisterResponse
import com.example.cycleme.repository.api.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.cycleme.data.Result

class RegisterViewModel: ViewModel() {
    fun postRegister(request: RegisterRequest): LiveData<Result<RegisterResponse>> {
        val resultRegisterResponse = MutableLiveData<Result<RegisterResponse>>()

        resultRegisterResponse.value = Result.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiConfig.getApiServiceMain().postRegister(request)
                }
                resultRegisterResponse.value = Result.Success(response)
            } catch (e: Exception) {
                Log.d("Register", e.message.toString())
                resultRegisterResponse.value = Result.Error(e.message.toString())
            }
        }

        return resultRegisterResponse
    }
}