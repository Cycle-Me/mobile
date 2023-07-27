package com.example.cycleme.ui.main.change_profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cycleme.model.ChangeProfileRequest
import com.example.cycleme.model.ChangeProfileResponse
import com.example.cycleme.repository.api.ApiConfig
import com.example.cycleme.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChangeProfileViewModel : ViewModel() {

    private val _changeProfileResult = MutableLiveData<Result<ChangeProfileResponse>>()
    val changeProfileResult: LiveData<Result<ChangeProfileResponse>> get() = _changeProfileResult

    fun changeProfile(
        id: String, name: String, email: String, password: String, confPassword: String
    ) {
        _changeProfileResult.value = Result.Loading

        viewModelScope.launch {
            try {
                val profileData = ChangeProfileRequest(name, email, password, confPassword)
                val response = withContext(Dispatchers.IO) {
                    ApiConfig.getApiServiceMain()
                        .changeProfile(id, profileData)
                }
                _changeProfileResult.value = Result.Success(response)
                Log.d("ChangeProfileSuccess", response.toString())
            } catch (e: Exception) {
                _changeProfileResult.value = Result.Error(e.message.toString())
                Log.d("ChangeProfileError", e.message.toString())
            }
        }
    }
}