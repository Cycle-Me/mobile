package com.example.cycleme.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

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
data class LoginDataOnSession(
    var isLogin: Boolean = false,
    var uuid: String? = null,
    var name: String? = null,
    var email: String? = null,
    var sessionID: String? = null,
    var password: String? = null
) : Parcelable

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

// Response
@Parcelize
data class StoriesResponse(

    @field:SerializedName("attachment")
    val attachment: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("uuid")
    val uuid: String? = null,

    @field:SerializedName("user")
    val user: UserDetail? = null
) : Parcelable

@Parcelize
data class UserDetail(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("email")
    val email: String? = null
) : Parcelable

data class LogoutResponse(
    @field:SerializedName("msg")
    val msg: String? = null
)

data class FeedbackData(
    val email: String,
    val subject: String,
    val message: String
)

data class ChangeProfileRequest(
    val name: String,
    val email: String,
    val password: String,
    val confPassword: String
)

data class ChangeProfileResponse(
    @field:SerializedName("msg")
    val msg: String? = null
)

data class UploadFeedResponse(

    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("createdStory")
    val createdStory: CreatedStory? = null
)

data class CreatedStory(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("attachment")
    val attachment: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("uuid")
    val uuid: String? = null,

    @field:SerializedName("userId")
    val userId: Int? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)




