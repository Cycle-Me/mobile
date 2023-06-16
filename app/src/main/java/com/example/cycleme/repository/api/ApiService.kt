package com.example.cycleme.repository.api

import com.example.cycleme.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @GET("stories")
    suspend fun getAllStories(): StoriesResponseItem

    @Multipart
    @POST("predict")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
    ): PredictResponse

    @POST("/")
    suspend fun postRecommendation(
        @Body request: RecommendationRequest
    ): RecommendationResponseList
}