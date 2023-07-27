package com.example.cycleme.repository.api

import com.example.cycleme.model.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiService {
    @POST("users/register")
    suspend fun postRegister(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("login")
    suspend fun postLogin(
        @Body request: LoginRequest
    ): LoginResponse

    @Multipart
    @POST("predict")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
    ): PredictResponse

    @POST("/")
    suspend fun postRecommendation(
        @Body request: RecommendationRequest
    ): List<RecommendationResponse>

    @GET("stories")
    suspend fun getStories(): List<StoriesResponse>

    @POST("feedback")
    suspend fun sendFeedback(
        @Body feedbackData: FeedbackData
    ): ResponseBody

    @DELETE("logout")
    suspend fun logout(): LogoutResponse

    @PATCH("users/update/{id}")
    suspend fun changeProfile(
        @Path("id") id: String,
        @Body profileData: ChangeProfileRequest
    ): ChangeProfileResponse

    @Multipart
    @POST("stories/create")
    suspend fun uploadFeed(
        @Part("description") description: String,
        @Part file: MultipartBody.Part,
    ): UploadFeedResponse
}