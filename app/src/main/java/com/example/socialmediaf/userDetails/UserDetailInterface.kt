package com.example.socialmediaf.userDetails

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserDetailInterface{

    @POST("api/accounts/create-profile")
    suspend fun createProfile(
        @Header("Authorization") token: String,
        @Body request: UserDetailRequest
    ): Response<UserDetailResponse>

    @GET("api/accounts/create-profile")
    suspend fun getProfile(
        @Header("Authorization") token: String,
    ): Response<UserData>

}

