package com.example.cycleme.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val confPassword: String? = null
)

data class RegisterResponse(
    @field:SerializedName("msg")
    val msg: String
)

data class LoginRequest(
    val email: String? = null,
    val password: String? = null
)

data class LoginResponse(
    val error: Boolean? = null,
    val msg: String? = null,
    val loginResult: LoginResult? = null
)

@Parcelize
data class LoginResult(
    val uuid: String? = null,
    val name: String? = null,
    val email: String? = null,
    val role: String? = null,
    val sessionID: String? = null
) : Parcelable

@Parcelize
data class CheckLogin(
    var isLogin: Boolean = false,
    var uuid: String? = null,
    var name: String? = null,
    var sessionID: String? = null
) : Parcelable

@Parcelize
data class StoriesResponse(
    @field:SerializedName("StoriesResponse")
    val storiesResponse: List<StoriesResponseItem?>? = null
) : Parcelable

@Parcelize
data class StoriesResponseItem(
    @field:SerializedName("attachment")
    val attachment: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("uuid")
    val uuid: String? = null,

    @field:SerializedName("user")
    val user: String? = null
) : Parcelable

data class FileUploadResponse(
    @field:SerializedName("msg")
    val msg: String
)

@Parcelize
data class PredictResponse(
    @field:SerializedName("accuracy")
    val accuracy: String,

    @field:SerializedName("class_label")
    val class_label: String
) : Parcelable

data class RecommendationRequest(
    val category: String? = null,
)

data class RecommendationResponseList(
    @field:SerializedName("RecommendationResponseList")
    val recommendationResponseList: List<RecommendationResponse?>? = null
)

@Parcelize
data class RecommendationResponse(
    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("desc")
    val desc: String? = null
) : Parcelable


